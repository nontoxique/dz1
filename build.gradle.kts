plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.26.3")
    implementation("io.rest-assured:rest-assured:6.0.1")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    // Source: https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.49.0")
    // Source: https://mvnrepository.com/artifact/com.codeborne/selenide
    implementation("com.codeborne:selenide:7.18.2")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
    jvmArgs("-Dfile.encoding=UTF-8", "-Dconsole.encoding=UTF-8")
}
tasks.withType<JavaExec> {
    jvmArgs(
        "-Dfile.encoding=UTF-8",
        "-Dconsole.encoding=UTF-8",
        "-Dstdout.encoding=UTF-8",
        "-Dstderr.encoding=UTF-8"
    ) }

tasks.test {
    useJUnitPlatform()
}
tasks.register("over") {
    doLast { println("Test run is over") }
}
tasks.register<Test>("allTests") {
    group = "Test"
    testClassesDirs = tasks.test.get().testClassesDirs
    classpath = tasks.test.get().classpath
    useJUnitPlatform {
        includeTags("smoke")
        excludeTags("dz3")
    }
}
tasks.named("over") {
    dependsOn("allTests")
}
tasks.register<Test>("APItests") {
    group = "Test"
    testClassesDirs = tasks.test.get().testClassesDirs
    classpath = tasks.test.get().classpath
    useJUnitPlatform {
        includeTags("APItestdz")
    }
}