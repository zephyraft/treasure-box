dependencies {
    val esVersion: String by project
    implementation("org.elasticsearch.client", "elasticsearch-rest-high-level-client", esVersion)
    implementation("commons-logging", "commons-logging", "1.2")
}