package zephyr.flink;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import zephyr.flink.model.Alert;
import zephyr.flink.model.Event;
import zephyr.flink.model.SubEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CEPDemo {

    private static final List<Event> list = new ArrayList<>();

    static {
        list.add(new Event(20, "hello"));
        list.add(new Event(42, "hello"));
        list.add(new SubEvent(51, "hello", 13.3D));
        list.add(new Event(55, "hello"));
        list.add(new Event(60, "end"));
        list.add(new Event(61, "hello"));

        list.add(new Event(20, "hello2"));
        list.add(new Event(42, "hello2"));
        list.add(new SubEvent(51, "hello2", 13.3D));
        list.add(new Event(55, "hello2"));
        list.add(new Event(60, "end2"));
        list.add(new Event(61, "hello2"));
    }

    public static void main(String[] args) throws Exception {
        // 初始化对象
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取数据
        DataStreamSource<Event> input = env.fromCollection(list);

        Pattern<Event, ?> pattern2 = Pattern.<Event>begin("rep").where(new SimpleCondition<Event>() {
            @Override
            public boolean filter(Event value) throws Exception {
                return value.getId() == 20;
            }
        }).oneOrMore();

        Pattern<Event, ?> pattern = Pattern.<Event>begin("start").where(
                new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event event) {
                        return event.getId() == 42;
                    }
                }
        ).next("middle").subtype(SubEvent.class).where(
                new SimpleCondition<SubEvent>() {
                    @Override
                    public boolean filter(SubEvent subEvent) {
                        return subEvent.getVolume() >= 10.0;
                    }
                }
        ).followedBy("end").where(
                new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event event) {
                        return event.getName().equals("end");
                    }
                }
        );

        PatternStream<Event> patternStream = CEP.pattern(input, pattern);

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
