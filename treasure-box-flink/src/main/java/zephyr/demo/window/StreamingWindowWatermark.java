package zephyr.demo.window;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://www.jianshu.com/p/2e87abfe89ff
@SuppressWarnings({"Convert2Lambda", "RedundantThrows"})
@Slf4j
public class StreamingWindowWatermark {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final int BoundedOutSeconds = 10;

    // nc -lk 8848
    public static void main(String[] args) throws Exception {
        //定义socket的端口号
        int port = 8848;
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置使用eventtime，默认是使用processtime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //设置并行度为1,默认并行度是当前机器的cpu数量
        env.setParallelism(1);
        //连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("localhost", port, "\n");
        //解析输入的数据,每行数据按逗号分隔
        DataStream<Tuple2<String, Long>> inputMap = text.map(new MapFunction<String, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(String value) throws Exception {
                String[] arr = value.split(",");
                return new Tuple2<>(arr[0], Long.parseLong(arr[1]));
            }
        });

        //抽取timestamp和生成watermark
        DataStream<Tuple2<String, Long>> waterMarkStream = inputMap.assignTimestampsAndWatermarks(
                WatermarkStrategy
                        .<Tuple2<String, Long>>forBoundedOutOfOrderness(Duration.ofSeconds(BoundedOutSeconds)) // 最大允许的乱序时间是10s
                        .withTimestampAssigner((element, previousElementTimestamp) -> {
                            long timestamp = element.f1;
                            long currentMaxTimestamp = Math.max(timestamp, previousElementTimestamp);
                            //设置多并行度时获取线程id
                            long id = Thread.currentThread().getId();
                            log.info("extractTimestamp=======>" + ",currentThreadId:" + id + ",key:" + element.f0 + ",eventtime:[" + element.f1 + "|"
                                    + dateTimeFormatter.format(timestampToLocalDateTime(element.f1)) + "]," + "currentMaxTimestamp:[" + currentMaxTimestamp + "|"
                                    + dateTimeFormatter.format(timestampToLocalDateTime(currentMaxTimestamp)) + "],watermark:[" + (currentMaxTimestamp - BoundedOutSeconds * 1000L) + "|"
                                    + dateTimeFormatter.format(timestampToLocalDateTime((currentMaxTimestamp - BoundedOutSeconds * 1000L))) + "]"
                            );
                            return timestamp;
                        }));

        //保存被丢弃的数据
        OutputTag<Tuple2<String, Long>> outputTag = new OutputTag<Tuple2<String, Long>>("late-data") {
        };


        SingleOutputStreamOperator<String> window = waterMarkStream
                .keyBy(new KeySelector<Tuple2<String, Long>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Long> value) throws Exception {
                        return value.f0;
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.seconds(3)))//按照消息的EventTime分配窗口，和调用TimeWindow效果一样
                .sideOutputLateData(outputTag)
                .apply(new WindowFunction<Tuple2<String, Long>, String, String, TimeWindow>() {
                    // 对window内的数据进行排序，保证数据的顺序
                    @Override
                    public void apply(String key, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<String> out) throws Exception {
                        List<Long> arrarList = new ArrayList<>();
                        for (Tuple2<String, Long> next : input) {
                            //时间戳放到了arrarList里
                            arrarList.add(next.f1);
                        }
                        Collections.sort(arrarList);
                        String result = key + "," + arrarList.size() + ","
                                + dateTimeFormatter.format(timestampToLocalDateTime(arrarList.get(0))) + ","
                                + dateTimeFormatter.format(timestampToLocalDateTime(arrarList.get(arrarList.size() - 1))) + ","
                                + dateTimeFormatter.format(timestampToLocalDateTime(window.getStart())) + ","
                                + dateTimeFormatter.format(timestampToLocalDateTime(window.getEnd()));
                        out.collect(result);
                    }
                });

        //window.getSideOutput获取迟到的数据，把迟到的数据暂时打印到控制台，实际中可以保存到其他存储介质中
        DataStream<Tuple2<String, Long>> sideOutput = window.getSideOutput(outputTag);
        sideOutput.flatMap(new FlatMapFunction<Tuple2<String, Long>, Tuple2<String, String>>() {
            @Override
            public void flatMap(Tuple2<String, Long> stringLongTuple2, Collector<Tuple2<String, String>> collector) throws Exception {
                collector.collect(new Tuple2<>(stringLongTuple2.f0, "late!!! eventtime:" + stringLongTuple2.f1 + "|"
                        + dateTimeFormatter.format(timestampToLocalDateTime(stringLongTuple2.f1))));
            }
        }).print();

        //测试-把结果打印到控制台即可
        window.print();

        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("eventtime-watermark");

    }

    public static LocalDateTime timestampToLocalDateTime(Long currentMaxTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(currentMaxTimestamp), ZoneId.systemDefault());
    }
}
