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
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
    }
}
tasks.named("over") {
    dependsOn("allTests")
}