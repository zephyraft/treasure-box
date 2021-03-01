val jmhVersion: String by project

dependencies {
    implementation("org.openjdk.jmh", "jmh-core", jmhVersion)
    annotationProcessor("org.openjdk.jmh", "jmh-generator-annprocess", jmhVersion)
}
