val poiVersion: String by project

dependencies {
    implementation("org.apache.poi", "poi", poiVersion)
    implementation("org.apache.poi", "poi-ooxml", poiVersion)
}
