val kryoVersion: String by project
val roaringBitmapVersion: String by project

dependencies {
    implementation("org.roaringbitmap", "RoaringBitmap", roaringBitmapVersion)
    implementation("com.esotericsoftware", "kryo", kryoVersion)
}
