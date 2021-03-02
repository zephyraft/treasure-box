plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.kafka", "spring-kafka")
    implementation("com.fasterxml.jackson.core", "jackson-databind")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")
    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")

    implementation("io.confluent:kafka-avro-serializer:6.0.1") {
        exclude("org.apache.kafka", "kafka-clients")
    }
}
