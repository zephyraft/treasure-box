plugins {
    // 需要生成时 把apply改为true
    id("org.jruyi.thrift") apply false
}

dependencies {
    val libthriftVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("org.apache.thrift", "libthrift", libthriftVersion)
}

if (plugins.hasPlugin("org.jruyi.thrift")) {
    tasks.named<org.jruyi.gradle.thrift.plugin.CompileThrift>("compileThrift") {
//    outputDir("$projectDir/src/main/thrift")
        createGenFolder(false)
    }
}
