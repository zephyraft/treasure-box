plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}
