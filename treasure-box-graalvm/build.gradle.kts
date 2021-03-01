plugins {
    id("org.mikeneck.graalvm-native-image")
}

val myGraalVmHome: String by project

val graalvmVersion: String by project
val guavaVersion: String by project

dependencies {
    implementation("org.graalvm.sdk", "graal-sdk", graalvmVersion)
    implementation("org.graalvm.js", "js", graalvmVersion)
    implementation("com.google.guava", "guava", guavaVersion)
}

nativeImage {
    graalVmHome = myGraalVmHome
    mainClass = "zephyr.nativeimage.NativeImageDemo"
    executableName = "nativeTest"
    outputDirectory = file("$buildDir/bin")
}