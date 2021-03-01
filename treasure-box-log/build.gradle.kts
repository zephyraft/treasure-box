val logbackContribVersion: String by project
val logstashLogbackEncoderVersion: String by project

dependencies {
    implementation("org.slf4j", "slf4j-api")

    // === logback start ===
    implementation("ch.qos.logback", "logback-classic")

    // 官方社区贡献，很久没维护了
    implementation("ch.qos.logback.contrib", "logback-json-classic", logbackContribVersion)
    implementation("ch.qos.logback.contrib", "logback-jackson", logbackContribVersion)
    implementation("com.fasterxml.jackson.core", "jackson-databind")

    // logstash维护的
    implementation("net.logstash.logback", "logstash-logback-encoder", logstashLogbackEncoderVersion)

    // === logback end ===
}