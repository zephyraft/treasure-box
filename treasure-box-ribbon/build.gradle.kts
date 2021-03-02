plugins {
    id("org.springframework.boot")
}

dependencies {
    val netflixRibbonVersion: String by project

    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.cloud", "spring-cloud-starter-netflix-ribbon", netflixRibbonVersion)
}
