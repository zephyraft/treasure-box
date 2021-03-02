dependencies {
    val tribuoVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("org.tribuo:tribuo-all:$tribuoVersion")
}