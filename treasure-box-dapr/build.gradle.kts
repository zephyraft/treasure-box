plugins {
    id("org.springframework.boot")
}

val daprVersion = "1.0.0-rc-2"

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")

    implementation("io.dapr", "dapr-sdk", daprVersion)
    implementation("io.dapr", "dapr-sdk-springboot", daprVersion)

    // If needed, force conflict resolution for okhttp3.
    configurations.all {
        resolutionStrategy {
            this.force("com.squareup.okhttp3:okhttp:4.2.2")
        }
    }
}
