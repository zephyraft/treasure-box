plugins {
    id("org.mikeneck.graalvm-native-image")
}

dependencies {
    val graalvmVersion: String by project
    val guavaVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("org.graalvm.sdk", "graal-sdk", graalvmVersion)
    implementation("org.graalvm.js", "js", graalvmVersion)
    implementation("com.google.guava", "guava", guavaVersion)
}

nativeImage {
    val myGraalVmHome: String by project

    graalVmHome = myGraalVmHome
    mainClass = "zephyr.nativeimage.NativeImageDemo"
    executableName = "nativeTest"
    outputDirectory = file("$buildDir/bin")
}