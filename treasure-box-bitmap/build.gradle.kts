dependencies {
    val kryoVersion: String by project
    val roaringBitmapVersion: String by project

    implementation("ch.qos.logback", "logback-classic")
    implementation("ch.qos.logback", "logback-core")
    implementation("org.roaringbitmap", "RoaringBitmap", roaringBitmapVersion)
    implementation("com.esotericsoftware", "kryo", kryoVersion)
}
