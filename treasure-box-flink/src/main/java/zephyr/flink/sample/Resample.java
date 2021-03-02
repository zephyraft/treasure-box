package zephyr.flink.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

//
// kafka的metric topic中的数据：
//  {
//  timestamp: 2020.7.31 10:00:00,
//  host: db1,
//  metric name: os/cpu utilization,
//  value: 82.3
//  }
// resample模块：
//
// 用stream的方式从metric topic中读数据（考虑性能，可以采用micro batch）
// 用<host, metric name>作为2元组，hash生成一个metric id整型
// 逻辑上，每一个metric id形成一个metric stream
// 根据每个点的metric id，查询配置得到metric的type
// 针对每个metric stream，计算最近2个小时的数据点的采样间隔S=avg（相邻数据点的时间差）
// 读取的数据中，如果一个metric stream的2个相邻的点的时间间隔大于2*S，则在这2个点中间每隔S补1个点，value=NA
public class Resample {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final ConcurrentHashMap<Integer, Long> avgCacheMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, LocalDateTime> lastTimeCacheMap = new ConcurrentHashMap<>();

    // 思路
    // 滚动窗口实现 避免数据重复 窗口滚动频率定义好
    // 每条数据都加入平均值的计算，缓存时间戳和对应的差值平均值
    //
    public static void main(String[] args) throws Exception {

        // 初始化对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置时间特性
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 获取数据
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");
        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));

        // 定义side-output
        OutputTag<Metrics> avgOutputTag = new OutputTag<Metrics>("side-output-avg") {};
        OutputTag<Metrics> tsOutputTag = new OutputTag<Metrics>("side-output-ts") {};

        // 开始计算
        final SingleOutputStreamOperator<Metrics> mainDataStream = stream
//                .assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps()) // 水印确保顺序
                .flatMap((FlatMapFunction<String, Metrics>) (in, out) -> { // 生成一个个元组 (word, 1)
                    try {
                        final Metrics metrics = objectMapper.readValue(in, Metrics.class);
                        MetricMapper.fillingMetric(metrics);
//                        System.out.println("================ " + metrics);
                        out.collect(metrics);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .returns(Metrics.class)
                .keyBy(Metrics::getType)
                .timeWindow(Time.seconds(2L), Time.seconds(2L)) // 调用窗口操作, 设置窗口长度和滑动间隔
                .process(new ProcessWindowFunction<Metrics, Metrics, Integer, TimeWindow>() { // 全量聚合 计算时间差
                    @Override
                    public void process(Integer key, Context ctx, Iterable<Metrics> elements, Collector<Metrics> out) {
                        // 全局平均差 = 平均数与每个值的差再除个数
                        // 平均相邻差
                        // 全量聚合并排序
                        TreeSet<Metrics> orderedSet = new TreeSet<>(Comparator.comparing(Metrics::getTimestamp));

                        for (Metrics metrics : elements) {
                            orderedSet.add(metrics);
                        }

                        LocalDateTime lastTime = null;
                        boolean isFirst = true;
                        for (Metrics metrics : orderedSet) {
                            if (isFirst) {
                                // 与上一个窗口缓存的lastTime比较
                                if (lastTimeCacheMap.get(key) == null) {
                                    metrics.setTimeDiff(0);
                                } else {
                                    metrics.setTimeDiff(Duration.between(lastTimeCacheMap.get(key), metrics.getTimestamp()).toMillis());
                                }
                                isFirst = false;
                            } else {
                                if (lastTime == null) {
                                    metrics.setTimeDiff(0);
                                } else {
                                    metrics.setTimeDiff(Duration.between(lastTime, metrics.getTimestamp()).toMillis());
                                }
                                lastTime = metrics.getTimestamp();
                            }
                            out.collect(metrics);
                            // 输出到side-output
                            ctx.output(avgOutputTag, metrics);

//                            System.out.println("=====mainToAvg=====key=" + key + "metrics=" + metrics);
                        }

                        // 缓存最后一个time
                        if (lastTime != null) {
                            lastTimeCacheMap.put(key, lastTime);
                        }
                    }
                })
                .flatMap((FlatMapFunction<Metrics, Metrics>) (in, out) -> { // 此处插值
                    // 获取缓存的平均差值
                    Long s = avgCacheMap.get(in.getMetricId());

                    if (s != null && in.getTimeDiff() > 2 * s) {
                        long tempTimeDiff = in.getTimeDiff();
                        // 每隔s差一个NA值
                        while (tempTimeDiff > s) {
                            Metrics metrics = new Metrics();
                            metrics.setHost(metrics.getHost());
                            metrics.setTimeDiff(s);
                            metrics.setType(metrics.getType());
                            metrics.setMetricId(metrics.getMetricId());
                            metrics.setMetricName(metrics.getMetricName());
                            metrics.setValue("NA");
                            metrics.setTimestamp(metrics.getTimestamp().minusNanos(1000 * s));
                            System.out.println("metrics");
                            out.collect(metrics); // 插值
                            tempTimeDiff -= s;
                        }
                    }
                    out.collect(in);
                })
                .returns(Metrics.class)
                .process(new ProcessFunction<Metrics, Metrics>() {
                    @Override
                    public void processElement(Metrics value, Context ctx, Collector<Metrics> out) {
                        // 原样输出
                        System.out.println("main ===== ");
                        out.collect(value);
                        // 输出到side-output
//                        ctx.output(tsOutputTag, value);
                    }
                })
                ;

        // 计算平均值
        mainDataStream.getSideOutput(avgOutputTag)
//                .process(new ProcessFunction<Metrics, Metrics>() {
//                    @Override
//                    public void processElement(Metrics value, Context ctx, Collector<Metrics> out) {
//                        System.out.println("avg====" + value);
//                        out.collect(value);
//                    }
//                })
                .keyBy(Metrics::getType)
                .timeWindow(Time.hours(2L), Time.seconds(2L))
//                .process(new ProcessWindowFunction<Metrics, Metrics, Integer, TimeWindow>() {
//                    @Override
//                    public void process(Integer key, Context ctx, Iterable<Metrics> elements, Collector<Metrics> out) throws Exception {
//                        System.out.println("avgOutput====" + key);
//                    }
//                })
                .apply(new WindowFunction<Metrics, Long, Integer, TimeWindow>() {
                    @Override
                    public void apply(Integer key, TimeWindow window, Iterable<Metrics> input, Collector<Long> out) {
                        // 计算并缓存最近两小时的平均差值
                        long count = 0L;
                        long timeDiffSum = 0L;
                        for (Metrics metrics : input) {
                            timeDiffSum += metrics.getTimeDiff();
                            count++;
                        }
                        final long avg = timeDiffSum / count;
                        out.collect(avg);
                        avgCacheMap.put(key, avg);
                        System.out.println("=====avgOutput=====key=" + key + "avg=" + avg);
                    }
                })
        ;
//                .print();

        // 一部分根据type输出到kafka？
//        mainDataStream.addSink(new PrintSinkFunction<>());

        // FlinkKafkaProducer<String> myProducer = new FlinkKafkaProducer<>(
        //        "my-topic",                  // target topic
        //        new SimpleStringSchema(),    // serialization schema
        //        properties,                  // producer config
        //        FlinkKafkaProducer.Semantic.EXACTLY_ONCE); // fault-tolerance

        // 一部分写入hdfs？es？供后续ts模块批量调用
//        mainDataStream
//                .getSideOutput(tsOutputTag)
//                .addSink(new PrintSinkFunction<>())
//        ;
        //        .setParallelism(1) // 设置并行度

        env.execute("Resample");
    }
}
