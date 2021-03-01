package zephyr.demo;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.OldCsv;
import org.apache.flink.table.descriptors.Schema;

public class WordCountSQL {

    public static void main(String[] args) throws Exception {
        // create a TableEnvironment for specific planner batch or streaming
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inBatchMode().build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        // create a Table
        tableEnv
                .connect(new FileSystem().path("C:\\Users\\zephyr\\IdeaProjects\\treasure-box-gradle\\treasure-box-other\\treasure-box-flink\\src\\main\\resources\\score.csv"))
                .withFormat(new OldCsv())
                .withSchema(
                        new Schema()
                        .field("season", DataTypes.STRING())
                        .field("player", DataTypes.STRING())
                        .field("playNum", DataTypes.STRING())
                        .field("firstCourt", DataTypes.INT())
                        .field("time", DataTypes.DOUBLE())
                        .field("assists", DataTypes.DOUBLE())
                        .field("steals", DataTypes.DOUBLE())
                        .field("blocks", DataTypes.DOUBLE())
                        .field("scores", DataTypes.DOUBLE())
                )
                .createTemporaryTable("score");

        // register an output Table
        tableEnv
                .connect(new FileSystem().path("C:\\Users\\zephyr\\IdeaProjects\\treasure-box-gradle\\treasure-box-other\\treasure-box-flink\\src\\main\\resources\\tableResult.csv"))
                .withFormat(new OldCsv().writeMode("OVERWRITE"))
                .withSchema(
                        new Schema()
                                .field("player", DataTypes.STRING())
                                .field("num", DataTypes.BIGINT())
                )
                .createTemporaryTable("outputTable");

        // create a Table object from a Table API query
        Table tapiResult = tableEnv.sqlQuery("select player, count(season) as num from score group by player order by num desc limit 3");
        tapiResult.execute().print();
        tapiResult.executeInsert("outputTable");
    }

    public static class PlayerData {
        /**
         * 赛季，球员，出场，首发，时间，助攻，抢断，盖帽，得分
         */
        public String season;
        public String player;
        public String playNum;
        public Integer firstCourt;
        public Double time;
        public Double assists;
        public Double steals;
        public Double blocks;
        public Double scores;

        @Override
        public String toString() {
            return "PlayerData{" +
                    "season='" + season + '\'' +
                    ", player='" + player + '\'' +
                    ", playNum='" + playNum + '\'' +
                    ", firstCourt=" + firstCourt +
                    ", time=" + time +
                    ", assists=" + assists +
                    ", steals=" + steals +
                    ", blocks=" + blocks +
                    ", scores=" + scores +
                    '}';
        }
    }

    public static class PlayerResult {
        public String player;
        public String seasonCount;
    }
}
