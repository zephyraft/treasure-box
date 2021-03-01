plugins {
    id("org.springframework.boot")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val mybatisStarterVersion: String by project
val junitVersion: String by project
val mockitoVersion: String by project
val hamcrestVersion: String by project
val mockobjectsVersion: String by project
val awaitilityVersion: String by project
val webdrivermanagerVersion: String by project
val seleniumVersion: String by project
val guavaVersion: String by project

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-jdbc")
    implementation("org.mybatis.spring.boot", "mybatis-spring-boot-starter", mybatisStarterVersion)
    implementation("javax.servlet", "javax.servlet-api")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot", "mybatis-spring-boot-starter-test", mybatisStarterVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("junit", "junit", junitVersion)

    implementation("com.h2database", "h2")

    testImplementation("org.mockito", "mockito-core", mockitoVersion)
    testImplementation("org.hamcrest", "hamcrest", hamcrestVersion)
    testImplementation("mockobjects", "mockobjects", mockobjectsVersion)
    testImplementation("org.awaitility", "awaitility", awaitilityVersion)

    testImplementation("io.github.bonigarcia", "webdrivermanager", webdrivermanagerVersion) // 安装浏览器驱动程序
    testImplementation("org.seleniumhq.selenium", "selenium-java", seleniumVersion) // 功能测试
    testImplementation("com.google.guava", "guava", guavaVersion)
}
