dependencies {
    val hanlpVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("com.hankcs", "hanlp", hanlpVersion)
}
