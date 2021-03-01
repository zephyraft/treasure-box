package zephyr.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

public class BatchWordCountStandalone {

    public static void main(String[] args) throws Exception {
        final String input = "C:\\Users\\zephyr\\IdeaProjects\\treasure-box-gradle\\treasure-box-other\\treasure-box-flink\\src\\main\\resources\\workcount.txt";
        final String output = "C:\\Users\\zephyr\\IdeaProjects\\treasure-box-gradle\\treasure-box-other\\treasure-box-flink\\src\\main\\resources\\2.txt";

        // 初始化对象
        final ExecutionEnvironment env = ExecutionEnvironment.createRemoteEnvironment("172.17.145.254", 8081, "C:\\Users\\zephyr\\IdeaProjects\\treasure-box-gradle\\treasure-box-other\\treasure-box-flink\\build\\libs\\treasure-box-flink-1.0-SNAPSHOT.jar");
        // 获取数据
        final DataSource<String> data = env.readTextFile(input);
        // 开始计算
        data
                .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
                    String[] splits = value.split("\\s");
                    for (String word : splits) {
                        if (word.length() > 0) {
                            out.collect(new Tuple2<>(word, 1));
                        }
                    }
                })
                .returns(TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {}))
                .groupBy(0)
                .sum(1) // 聚合
                .setParallelism(1) // 设置并行度
                .writeAsText(output); //, FileSystem.WriteMode.OVERWRITE

        env.execute("BatchWordCount");
    }

}
