plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.cloud", "spring-cloud-gateway-webflux")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("io.projectreactor", "reactor-test")
}
