val guavaVersion: String by project
val cglibVersion: String by project

dependencies {
    implementation("com.google.guava", "guava", guavaVersion)
    implementation("cglib", "cglib", cglibVersion)
}
