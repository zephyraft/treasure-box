plugins {
    // 需要生成时 把apply改为true
    id("org.jruyi.thrift") apply false
}

val libthriftVersion: String by project

dependencies {
    implementation("org.apache.thrift", "libthrift", libthriftVersion)
}

if (plugins.hasPlugin("org.jruyi.thrift")) {
    tasks.named<org.jruyi.gradle.thrift.plugin.CompileThrift>("compileThrift") {
//    outputDir("$projectDir/src/main/thrift")
        createGenFolder(false)
    }
}
