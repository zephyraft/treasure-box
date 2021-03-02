plugins {
    id("org.springframework.boot")
}


dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")

    implementation("org.springframework.boot", "spring-boot-starter-cache")
    implementation("org.springframework.boot", "spring-boot-starter-data-redis")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}