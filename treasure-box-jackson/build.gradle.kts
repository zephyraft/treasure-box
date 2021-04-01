dependencies {
    val msgpackVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("com.fasterxml.jackson.core", "jackson-databind")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.module", "jackson-module-parameter-names")

    // msgpack
    implementation("org.msgpack", "jackson-dataformat-msgpack", msgpackVersion)
    // cbor
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-cbor")
}
