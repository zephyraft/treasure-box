dependencies {
    val prometheusClientVersion: String by project

    implementation("io.prometheus", "simpleclient", prometheusClientVersion)
    implementation("io.prometheus", "simpleclient_httpserver", prometheusClientVersion)
}