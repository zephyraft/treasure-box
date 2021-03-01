dependencies {
}

java {
    modularity.inferModulePath.set(true) // jdk9 模块化
}

configure<JavaPluginConvention> {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}
