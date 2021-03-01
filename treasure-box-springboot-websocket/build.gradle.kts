plugins {
    id("org.springframework.boot")
}


dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")

    implementation("org.springframework.boot", "spring-boot-starter-websocket")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")

    // 前端依赖
    implementation("org.webjars", "webjars-locator-core")
    implementation("org.webjars", "sockjs-client", "1.0.2")
    implementation("org.webjars", "stomp-websocket", "2.3.3")
    implementation("org.webjars", "bootstrap", "3.3.7")
    implementation("org.webjars", "jquery", "3.1.1-1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}