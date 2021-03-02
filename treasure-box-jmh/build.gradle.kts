dependencies {
    val jmhVersion: String by project

    implementation("org.openjdk.jmh", "jmh-core", jmhVersion)
    annotationProcessor("org.openjdk.jmh", "jmh-generator-annprocess", jmhVersion)
}
