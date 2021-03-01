plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")

    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.boot", "spring-boot-starter-security")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}
