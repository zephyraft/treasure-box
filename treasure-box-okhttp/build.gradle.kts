dependencies {
    val okhttpVersion: String by project

    implementation("com.squareup.okhttp3", "okhttp", okhttpVersion)
    implementation("com.fasterxml.jackson.core", "jackson-databind")
}
