import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("base-conventions")
    id("com.github.johnrengelman.shadow")
}

tasks {
    val shadowJar = named<ShadowJar>("shadowJar") {
        archiveVersion.set(rootProject.version.toString())
        archiveClassifier.set("")

        val sJar: ShadowJar = this

        doFirst {
            sJar.dependencies {
                exclude(dependency("org.jetbrains:annotations:.*"))
            }
        }
    }
    named("build") {
        dependsOn(shadowJar)
    }
}