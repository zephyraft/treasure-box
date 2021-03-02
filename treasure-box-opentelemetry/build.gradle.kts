plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}
