plugins {
    `java-library`
    id("io.freefair.lombok")
}

val rootProperties: Map<String, *> = project.rootProject.properties
group = rootProperties["group"] as String + "." + rootProperties["id"] as String
version = rootProperties["version"] as String

repositories {
    gradlePluginPortal()

    mavenLocal()
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}