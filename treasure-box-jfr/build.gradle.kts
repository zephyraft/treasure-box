java {
    // withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    val log4j2Version: String by project

    implementation("org.apache.logging.log4j", "log4j-api", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-core", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", log4j2Version)
}
