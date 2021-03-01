package zephyr.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.functions.TimedOutPartialMatchHandler;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;
import java.util.Properties;

// cep 处理 已经被打好了全局流水标记，路径id，节点id的数据
// 如果是已经分组的（1）（1，2）（1，2，3），需要排序，压平后输出
public class DtraceCEPDemo {

    public static final String NODE_KEY = "n";
    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    // n0 -> n1 -> n3
    //    -> n2

    // {gk:1, n:1}
    // {gk:1, n:1}, {gk:1, n:0}
    // {gk:1, n:1}, {gk:1, n:0}, {gk:1, n:3}
    // 可以压平后处理

    // {}, {}, {}

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
                Map<String, String> msg = objectMapper.readValue(value, new TypeReference<Map<String, String>>() {
                });
                // TODO 匹配出path 和 globalValue
                String pathId = "p0";
                return new Event(pathId, msg.get(pathId), msg);
            }
        });

        // TODO ============== where 全写一样的 把 整个链路的每一个消息写下来，用where in的语义表达==============

        // 根据配置的rule构造Pattern
        Pattern<Event, ?> pattern = Pattern.<Event>begin("path0_0")
                .where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        String nodeValue = value.getMsg().get(NODE_KEY);
                        return nodeValue != null && nodeValue.equals("0");
                    }
                })
                .within(Time.seconds(10L))
                .followedBy("path0_1").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        String nodeValue = value.getMsg().get(NODE_KEY);
                        return nodeValue != null && nodeValue.equals("1");
                    }
                })
                .within(Time.seconds(10L))
                .followedBy("path0_3").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        String nodeValue = value.getMsg().get(NODE_KEY);
                        return nodeValue != null && nodeValue.equals("3");
                    }
                })
                .within(Time.seconds(10L));

        PatternStream<Event> patternStream = CEP.pattern(mappedInput.keyBy(Event::getGlobalValue), pattern);

        patternStream.process(new MyPatternProcessFunction());


        env.execute();

    }

    static class MyPatternProcessFunction<IN, OUT, T> extends PatternProcessFunction<IN, OUT> implements TimedOutPartialMatchHandler<IN> {

        @Override
        public void processMatch(Map<String, List<IN>> match, Context ctx, Collector<OUT> out) throws Exception {
            IN startEvent = match.get("start").get(0);
            IN endEvent = match.get("end").get(0);
        }

        @Override
        public void processTimedOutMatch(Map<String, List<IN>> match, Context ctx) throws Exception {
            IN startEvent = match.get("start").get(0);
//            ctx.output(null, new T(startEvent));
        }
    }


    private static class Event {
        private String path;
        private String globalValue;
        private Map<String, String> msg;

        public Event(String path, String globalValue, Map<String, String> msg) {
            this.path = path;
            this.globalValue = globalValue;
            this.msg = msg;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getGlobalValue() {
            return globalValue;
        }

        public void setGlobalValue(String globalValue) {
            this.globalValue = globalValue;
        }

        public Map<String, String> getMsg() {
            return msg;
        }

        public void setMsg(Map<String, String> msg) {
            this.msg = msg;
        }
    }

    private static class Path {
        private String name;
        private long globalTimeout; // 全局超时
        private String globalKey; // 全局key
        private Node link;

        public Path(String name, long globalTimeout, String globalKey, Node link) {
            this.name = name;
            this.globalTimeout = globalTimeout;
            this.globalKey = globalKey;
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getGlobalTimeout() {
            return globalTimeout;
        }

        public void setGlobalTimeout(long globalTimeout) {
            this.globalTimeout = globalTimeout;
        }

        public String getGlobalKey() {
            return globalKey;
        }

        public void setGlobalKey(String globalKey) {
            this.globalKey = globalKey;
        }

        public Node getLink() {
            return link;
        }

        public void setLink(Node link) {
            this.link = link;
        }
    }

    private static class Node {
        private String name;
        private String associateKey; // 关联key
        private String matchExpr; // TODO graalvm 解析matchExpr
        private long timeout; // 节点间超时
        private Node next; // 下一个节点

        public Node(String name, String associateKey, String matchExpr, long timeout, Node next) {
            this.name = name;
            this.associateKey = associateKey;
            this.matchExpr = matchExpr;
            this.timeout = timeout;
            this.next = next;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAssociateKey() {
            return associateKey;
        }

        public void setAssociateKey(String associateKey) {
            this.associateKey = associateKey;
        }

        public String getMatchExpr() {
            return matchExpr;
        }

        public void setMatchExpr(String matchExpr) {
            this.matchExpr = matchExpr;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
