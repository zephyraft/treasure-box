package zephyr.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import zephyr.demo.model.Alert;
import zephyr.demo.model.Event;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CEPDemo2 {

    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static void main(String[] args) throws Exception {
        // 初始化对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.250.127:9092");
        properties.setProperty("group.id", "cepdemo2");
        // 获取数据
        DataStreamSource<String> input = env.addSource(new FlinkKafkaConsumer<>("ceptest", new SimpleStringSchema(), properties));
        SingleOutputStreamOperator<Event> mappedInput = input.map(new MapFunction<String, Event>() {
            @Override
            public Event map(String value) throws Exception {
                return objectMapper.readValue(value, Event.class);
            }
        });

        Pattern<Event, ?> pattern = Pattern.<Event>begin("start").where(
                new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event event) throws Exception {
                        return event.getId() % 8 == 0;
                    }
                }
        );

        PatternStream<Event> patternStream = CEP.pattern(mappedInput, pattern);

        DataStream<Alert> result = patternStream.process(
                new PatternProcessFunction<Event, Alert>() {
                    @Override
                    public void processMatch(Map<String, List<Event>> match, Context ctx, Collector<Alert> out) throws Exception {
                        for (Map.Entry<String, List<Event>> entry : match.entrySet()) {
                            System.out.println(entry.getKey() + "===");
                            for (Event event : entry.getValue()) {
                                System.out.println(event.toString());
                            }
                        }
                    }
                });

        env.execute();

    }


}
