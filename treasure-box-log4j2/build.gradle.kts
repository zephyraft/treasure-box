dependencies {
    val log4j2Version: String by project
    val disruptorVersion: String by project

    implementation("org.apache.logging.log4j", "log4j-api", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-core", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-layout-template-json", log4j2Version)
    implementation("com.lmax", "disruptor", disruptorVersion)

    implementation("com.fasterxml.jackson.core", "jackson-databind")
}