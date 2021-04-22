dependencies {
    val arrowVersion: String by project
    val log4j2Version: String by project
    val okioVersion: String by project
    val fastUtilVersion: String by project

    implementation("org.apache.arrow", "arrow-vector", arrowVersion)
    implementation("org.apache.arrow", "arrow-memory-netty", arrowVersion)

    implementation("com.squareup.okio", "okio", okioVersion)
    implementation("it.unimi.dsi", "fastutil", fastUtilVersion)

    implementation("org.apache.logging.log4j", "log4j-api", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-core", log4j2Version)
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", log4j2Version)
}
