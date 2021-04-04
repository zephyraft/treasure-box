dependencies {
    val jolVersion: String by project
    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("com.fasterxml.jackson.core", "jackson-databind")
    implementation("org.openjdk.jol", "jol-core", jolVersion)
}
