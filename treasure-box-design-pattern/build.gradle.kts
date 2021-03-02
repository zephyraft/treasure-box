dependencies {
    val guavaVersion: String by project
    val cglibVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("com.google.guava", "guava", guavaVersion)
    implementation("cglib", "cglib", cglibVersion)
}
