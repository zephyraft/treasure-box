plugins {
    id("org.springframework.boot")
    id("com.google.cloud.tools.jib")
}

// https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin
jib {
    from {
        image = "openjdk:11-jdk-slim"
    }
    to {
        image = "zephyrinzephyr/built-with-jib"
        tags = setOf("test")
//        credHelper = "osxkeychain"
    }
    container {
        ports = listOf("8081/tcp")
// OCI 镜像push有问题
        format = com.google.cloud.tools.jib.api.buildplan.ImageFormat.Docker
//        jvmFlags = listOf("-Dmy.property=example.value", "-Xms512m", "-Xdebug")
//        mainClass = "mypackage.MyApp"
//        args = listOf("some", "args")
//        labels = mapOf("key1" to "value1", "key2" to "value2")
    }
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("io.micrometer", "micrometer-registry-prometheus")

    // 研究JarLauncher/WarLauncher引入
    compileOnly("org.springframework.boot", "spring-boot-loader")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}
