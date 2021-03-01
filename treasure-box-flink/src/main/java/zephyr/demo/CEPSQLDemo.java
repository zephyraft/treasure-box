package zephyr.demo;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.apache.flink.table.api.Expressions.$;

public class CEPSQLDemo {

    private static final List<Ticket> list = new ArrayList<>();
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            list.add(new Ticket("XYZ", 1, 10, format.parse("2018-09-17 10:00:02").getTime()));
            list.add(new Ticket("XYZ", 2, 11, format.parse("2018-09-17 10:00:03").getTime()));
            list.add(new Ticket("XYZ", 1, 12, format.parse("2018-09-17 10:00:04").getTime()));
            list.add(new Ticket("XYZ", 2, 13, format.parse("2018-09-17 10:00:05").getTime()));
            list.add(new Ticket("XYZ", 1, 14, format.parse("2018-09-17 10:00:06").getTime()));
            list.add(new Ticket("XYZ", 2, 16, format.parse("2018-09-17 10:00:07").getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 初始化对象
        final StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment stenv = StreamTableEnvironment.create(senv);

        // 获取数据
        DataStreamSource<Ticket> input = senv.fromCollection(list);
        senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        input.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Ticket>forBoundedOutOfOrderness(Duration.ofSeconds(6))
                .withTimestampAssigner((event, timestamp) -> event.time));

        TableResult dropResult = stenv.executeSql("DROP TABLE IF EXISTS ticker");
        stenv.createTemporaryView("ticker", input, $("symbol"), $("tax"), $("price"), $("time"));

        stenv.executeSql("select * from ticker limit 1").print();
        TableResult tableResult = stenv.executeSql(
                "SELECT *\n" +
                        " FROM ticker\n" +
                        "    MATCH_RECOGNIZE(\n" +
                        "        PARTITION BY symbol\n" +
                        "        ORDER BY `time`\n" +
                        "        MEASURES\n" +
                        "            C.price AS lastPrice\n" +
                        "        ONE ROW PER MATCH\n" +
                        "        AFTER MATCH SKIP PAST LAST ROW\n" +
                        "        PATTERN (A B* C)\n" +
                        "        DEFINE\n" +
                        "            A AS A.price > 10,\n" +
                        "            B AS B.price < 15,\n" +
                        "            C AS C.price > 12\n" +
                        "    )"
        );
        tableResult.print();

        stenv.execute("cep-sql");
    }

    //  symbol  tax   price          rowtime
    //======= ===== ======== =====================
    // XYZ     1     10       2018-09-17 10:00:02
    // XYZ     2     11       2018-09-17 10:00:03
    // XYZ     1     12       2018-09-17 10:00:04
    // XYZ     2     13       2018-09-17 10:00:05
    // XYZ     1     14       2018-09-17 10:00:06
    // XYZ     2     16       2018-09-17 10:00:07
    public static class Ticket {
        public String symbol;
        public int tax;
        public int price;
        public long time;

        public Ticket() {
        }

        public Ticket(String symbol, int tax, int price, long time) {
            this.symbol = symbol;
            this.tax = tax;
            this.price = price;
            this.time = time;
        }
    }


}
