package zephyr.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * 用窗口操作的方式进行WordCount
 * 实现每隔1秒对最近2秒内的数据进行聚合操作
 * nc -lk 6666
 */
public class WordCount {

    public static void main(String[] args) throws Exception {
        // 获取服务数据
        final int port = 6666;
//        ParameterTool parameterTool = ParameterTool.fromArgs(args);
//        port = parameterTool.getInt("port");
        final String hostName = "172.17.145.254";
        // 初始化对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取数据
        final DataStreamSource<String> data = env.socketTextStream(hostName, port);
        // 开始计算
        data
                .flatMap((FlatMapFunction<String, WordWithCount>) (in, out) -> { // 生成一个个元组 (word, 1)
                    String[] splits = in.split("\\s");
                    for (String word : splits) {
                        out.collect(new WordWithCount(word, 1L));
                    }
                })
                .returns(WordWithCount.class)
//                .keyBy("word") // 将元组按key分组
                .keyBy(WordWithCount::getWord) // 将元组按key分组
                .timeWindow(Time.seconds(2L), Time.seconds(1L)) // 调用窗口操作, 设置窗口长度和滑动间隔
                .sum("count") // 等价于 .reduce((ReduceFunction<WordWithCount>) (value1, value2) -> new WordWithCount(value1.getWord(), value1.getCount() + value2.getCount()));
                .setParallelism(1) // 设置并行度
                .print();
        env.execute("WordCount");
    }

}
