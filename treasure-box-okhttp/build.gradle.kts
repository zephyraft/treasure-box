val okhttpVersion: String by project

dependencies {
    implementation("com.squareup.okhttp3", "okhttp", okhttpVersion)
    implementation("com.fasterxml.jackson.core", "jackson-databind")
}
