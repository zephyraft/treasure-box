package zephyr.flink.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.Properties;

public class ResampleTest {

    public static void main(String[] args) throws Exception {


        // 初始化对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");
        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));

        OutputTag<Metrics> tsOutputTag = new OutputTag<Metrics>("side-output-ts") {};

        // 开始计算
        final SingleOutputStreamOperator<Metrics> mainDataStream = stream
                .assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps()) // 水印确保顺序
                .flatMap((FlatMapFunction<String, Metrics>) (in, out) -> { // 生成一个个元组 (word, 1)
//                    System.out.println(in);
                    try {
                        final Metrics metrics = Jackson.objectMapper.readValue(in, Metrics.class);
                        MetricMapper.fillingMetric(metrics);
                        out.collect(metrics);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .returns(Metrics.class)
                .process(new ProcessFunction<Metrics, Metrics>() {
                    @Override
                    public void processElement(Metrics value, Context ctx, Collector<Metrics> out) {
                        // 原样输出
                        out.collect(value);
                        // 输出到side-output
                        ctx.output(tsOutputTag, value);
                    }
                });

        mainDataStream
                .process(new ProcessFunction<Metrics, Metrics>() {
                    @Override
                    public void processElement(Metrics value, Context ctx, Collector<Metrics> out) {
                        // 原样输出
                        System.out.println("main-output" + value);
                    }
                });

        mainDataStream
                .getSideOutput(tsOutputTag)
                .process(new ProcessFunction<Metrics, Metrics>() {
                    @Override
                    public void processElement(Metrics value, Context ctx, Collector<Metrics> out) {
                        // 原样输出
                        System.out.println("side-output" + value);
                    }
                });
        //  执行job
        env.execute("testKafka");
    }

    static class Jackson {
        public static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
