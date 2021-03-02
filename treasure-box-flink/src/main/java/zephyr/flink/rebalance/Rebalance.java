package zephyr.flink.rebalance;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

// https://www.jianshu.com/p/2e87abfe89ff
@SuppressWarnings({"Convert2Lambda", "RedundantThrows"})
@Slf4j
public class Rebalance {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final int BoundedOutSeconds = 10;

    // nc -lk 8848
    // a,1
    // a,2
    // b,3
    // c,4
    // a,5
    // a,6
    public static void main(String[] args) throws Exception {
        //定义socket的端口号
        int port = 8848;
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //连接socket获取输入的数据
        DataStream<String> textStream = env.socketTextStream("localhost", port, "\n");
        //解析输入的数据,每行数据按逗号分隔
        SingleOutputStreamOperator<Tuple2<String, Long>> inputMap = textStream.map(new MapFunction<String, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(String value) throws Exception {
                String[] arr = value.split(",");
                return new Tuple2<>(arr[0], Long.parseLong(arr[1]));
            }
        });

        // 两阶段聚合法解决Flink keyBy() 数据倾斜
        // //加随机前缀
        //	val prifixrdd=maprdd.map(x=>{
        //	   val prifix=new Random().nextInt(10)
        //	   (prifix+"_"+x._1,x._2)
        //	 })
        //
        // //加上随机前缀的key进行局部聚合
        // val tmprdd=prifixrdd.reduceByKey(_+_)
        //
        // //去除随机前缀
        // val newrdd=tmprdd.map(x=> (x._1.split("_")(1),x._2))
        //
        // //最终聚合
        // newrdd.reduceByKey(_+_).foreach(print)
        inputMap.map(new MapFunction<Tuple2<String, Long>, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(Tuple2<String, Long> value) throws Exception {
                value.f0 += "@" + ThreadLocalRandom.current().nextInt(20); //
                return value;
            }
        }).keyBy(new KeySelector<Tuple2<String, Long>, String>() {
            @Override
            public String getKey(Tuple2<String, Long> value) throws Exception {
                return value.f0;
            }
        }).map(new MapFunction<Tuple2<String, Long>, Tuple2<String, Long>>() { // 去除key
            @Override
            public Tuple2<String, Long> map(Tuple2<String, Long> value) throws Exception {
                String key = value.f0;
                key = key.substring(0, key.indexOf('@'));
                value.f0 = key;
                return value;
            }
        }).print();

        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("keyby data skew");

    }
}
