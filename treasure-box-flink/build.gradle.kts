import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
}

val scalaBinaryVersion: String by project
val flinkVersion: String by project
val junitVersion: String by project
val hiveVersion: String by project
val hadoopVersion: String by project

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        exclude(dependency("org.apache.flink:flink-java:.*"))
        exclude(dependency("org.apache.flink:flink-streaming-java_.*:.*"))
        exclude(dependency("org.apache.flink:flink-clients_.*:.*"))
    }
}

dependencies {
    implementation("org.apache.flink", "flink-java", flinkVersion)
    implementation("org.apache.flink", "flink-streaming-java_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-clients_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-connector-kafka_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-table-api-java", flinkVersion)
    implementation("org.apache.flink", "flink-table-common", flinkVersion)
    implementation("org.apache.flink", "flink-table-api-java-bridge_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-table-planner_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-table-planner-blink_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-table-runtime-blink_${scalaBinaryVersion}", flinkVersion)
//    implementation("org.apache.flink", "flink-cep_${scalaBinaryVersion}", "${flinkVersion}_reform")
    implementation("org.apache.flink", "flink-cep_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-statebackend-rocksdb_${scalaBinaryVersion}", flinkVersion)

    implementation("org.apache.flink", "flink-sql-connector-hive-${hiveVersion}_${scalaBinaryVersion}", flinkVersion)
    implementation("org.apache.flink", "flink-shaded-hadoop-2-uber", "2.4.1-10.0") {
        exclude("org.slf4j", "slf4j-log4j12")
    }

    implementation("com.fasterxml.jackson.core", "jackson-databind")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")

    testImplementation("org.apache.flink", "flink-test-utils-junit", flinkVersion)
    testImplementation("org.apache.flink", "flink-test-utils_${scalaBinaryVersion}", flinkVersion)
    testImplementation("junit", "junit", junitVersion)
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}