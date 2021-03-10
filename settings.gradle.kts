rootProject.name = "treasure-box"

include(
    "treasure-box-asm",
    "treasure-box-bitmap",
    "treasure-box-concurrent",
    "treasure-box-dapr",
    "treasure-box-design-pattern",
    "treasure-box-disruptor",
    "treasure-box-elasticsearch",
    "treasure-box-flink",
    "treasure-box-gateway",
    "treasure-box-git",
    "treasure-box-graalvm",
    "treasure-box-guava",
    "treasure-box-jackson",
    "treasure-box-javassist",
    "treasure-box-jdk8",
    "treasure-box-jdk9",
    "treasure-box-jdk9:module-client",
    "treasure-box-jdk9:module-lib",
    "treasure-box-jdk10",
    "treasure-box-jdk11",
    "treasure-box-jfr",
    "treasure-box-jmh",
    "treasure-box-just",
    "treasure-box-jvm",
    "treasure-box-jwt",
    "treasure-box-kafka",
    "treasure-box-kotlin",
    "treasure-box-log4j2",
    "treasure-box-logback",
    "treasure-box-lombok",
    "treasure-box-markdown",
    "treasure-box-netty",
    "treasure-box-nlp",
    "treasure-box-okhttp",
    "treasure-box-opentelemetry",
    "treasure-box-poi",
    "treasure-box-prometheus",
    "treasure-box-reactor",
    "treasure-box-redis",
    "treasure-box-ribbon",
    "treasure-box-rxjava",
    "treasure-box-sentinel",
    "treasure-box-springboot",
    "treasure-box-springboot-cache",
    "treasure-box-springboot-security",
    "treasure-box-springboot-websocket",
    "treasure-box-system-design",
    "treasure-box-tdd",
    "treasure-box-thrift",
    "treasure-box-tribuo",
    "treasure-box-weka",
    "treasure-box-zookeeper",
    ""
)

pluginManagement {
    plugins {
        val pluginKotlinVersion: String by settings
        val pluginSpringBootVersion: String by settings
        val pluginJibVersion: String by settings
        val pluginShadowVersion: String by settings
        val pluginThriftVersion: String by settings
        val pluginNativeImageVersion: String by settings

        kotlin("jvm").version(pluginKotlinVersion).apply(false)
        kotlin("kapt").version(pluginKotlinVersion).apply(false)
        kotlin("plugin.serialization").version(pluginKotlinVersion).apply(false)
        kotlin("plugin.spring").version(pluginKotlinVersion).apply(false)
        id("org.springframework.boot").version(pluginSpringBootVersion).apply(false)
        id("com.google.cloud.tools.jib").version(pluginJibVersion).apply(false)
        id("com.github.johnrengelman.shadow").version(pluginShadowVersion).apply(false)
        id("org.jruyi.thrift").version(pluginThriftVersion).apply(false)
        id("org.mikeneck.graalvm-native-image").version(pluginNativeImageVersion).apply(false)
    }
}