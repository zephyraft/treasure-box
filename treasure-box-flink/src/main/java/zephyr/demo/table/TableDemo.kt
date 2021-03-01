package zephyr.demo.table

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment
import org.apache.flink.table.catalog.hive.HiveCatalog
import org.apache.flink.types.Row

fun main() {
    val env = StreamExecutionEnvironment.getExecutionEnvironment()
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tableEnv = StreamTableEnvironment.create(env, settings)


    val name = "hive"
    val defaultDatabase = "default"
    val hiveConfDir =
        "/Users/zhonghaoyuan/IdeaProjects/treasure-box-gradle/treasure-box-middleware/treasure-box-flink/src/main/resources"
    val version = "3.1.2"

    val hive = HiveCatalog(name, null, hiveConfDir, version)

    tableEnv.registerCatalog("hive", hive)

    // set the HiveCatalog as the current catalog of the session
    tableEnv.useCatalog("hive")
    tableEnv.useDatabase("default")

    // language=sql
    @Suppress("SqlDialectInspection", "SqlNoDataSourceInspection")
    tableEnv.executeSql(
        """
        CREATE TABLE `hive`.`default`.`TEST` (
            `name` VARCHAR(32),
            `age` INT,
            `timestamp` BIGINT
        )
        COMMENT ''
        WITH (
            'connector' = 'kafka',
            'format' = 'json',
            'properties.bootstrap.servers' = '172.16.1.42:9094',
            'properties.group.id' = 'flink',
            'topic' = 'metastore-test'
        )
    """.trimIndent()
    )

    val table = tableEnv.sqlQuery("select * from TEST")
    tableEnv.toRetractStream(table, Row::class.java).print()

}