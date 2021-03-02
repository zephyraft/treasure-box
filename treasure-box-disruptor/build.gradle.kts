dependencies {
    val disruptorVersion: String by project

    implementation("com.lmax", "disruptor", disruptorVersion)
}