plugins {
    java
    `maven-publish`
    signing
    kotlin("jvm") apply false
    id("com.github.johnrengelman.shadow") apply false
    id("org.springframework.boot") apply false
    id("org.jruyi.thrift") apply false
}

// 子模块公共配置
subprojects {

    group = "zephyr"
    version = "1.0-SNAPSHOT"

    // 声明公共插件
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    java {
        // withSourcesJar()
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    val springBootDependenciesVersion: String by project
    val springCloudDependenciesVersion: String by project
    val springStatemachineBomVersion: String by project
    val springDataReleaseTrainVersion: String by project
    val reactorBomVersion: String by project
    val jacksonBomVersion: String by project
    val junitJupiterBomVersion: String by project
    val lombokVersion: String by project

    // 公共依赖
    dependencies {
        // import a BOM
        implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootDependenciesVersion}"))
        implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudDependenciesVersion}"))
        implementation(platform("org.springframework.statemachine:spring-statemachine-bom:${springStatemachineBomVersion}"))
        implementation(platform("org.springframework.data:spring-data-releasetrain:${springDataReleaseTrainVersion}"))
        implementation(platform("io.projectreactor:reactor-bom:${reactorBomVersion}"))
        implementation(platform("com.fasterxml.jackson:jackson-bom:${jacksonBomVersion}"))
        testImplementation(platform("org.junit:junit-bom:${junitJupiterBomVersion}"))

        // bom不会对annotationProcessor生效
        annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
        testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")

        // import dependencies
        implementation("org.slf4j", "slf4j-api")
        implementation("ch.qos.logback", "logback-classic")
        implementation("ch.qos.logback", "logback-core")
        compileOnly("org.projectlombok", "lombok")
        testCompileOnly("org.projectlombok", "lombok")
    }

    // 声明仓库
    repositories {
        mavenCentral()
        maven {
            setUrl("https://maven.aliyun.com/repository/public")
        }
        maven {
            setUrl("https://packages.confluent.io/maven/")
            content {
                includeGroupByRegex("io.confluent.*")
            }
        }
    }

    // 发布maven配置
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                //如果是war包填写components.web，如果是jar包填写components.java
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }
        // https://repomanage.rdc.aliyun.com/my/repo
        repositories {
            val releasesRepoUrl = "https://repo.rdc.aliyun.com/repository/130664-release-JFGcNj/"
            val snapshotsRepoUrl = "https://repo.rdc.aliyun.com/repository/130664-snapshot-qzXmR6/"
            val url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            maven {
                credentials {
                    username = "GjYBkM"
                    password = "VEiyG25YmR"
                }
                setUrl(url)
            }
        }
    }

    // 签名配置
    signing {
        // SNAPSHOT不执行签名
        if (!version.toString().endsWith("SNAPSHOT")) {
            sign(publishing.publications["maven"])
        }
    }

    // javadoc配置
    tasks.javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}
