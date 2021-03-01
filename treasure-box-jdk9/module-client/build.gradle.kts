
dependencies {
    implementation(project(":treasure-box-jdk9:module-lib"))
}

java {
    modularity.inferModulePath.set(true) // jdk9 模块化
}
