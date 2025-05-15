import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("base-conventions")
    id("com.github.johnrengelman.shadow")
}

tasks {
    val jarFilesDir = rootDir.resolve(".jarfiles")
    val shadowJar = named<ShadowJar>("shadowJar") {
        archiveVersion.set(rootProject.version.toString())
        archiveClassifier.set("")
        destinationDirectory = jarFilesDir

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
    named("clean") {
        doLast {
            jarFilesDir.deleteRecursively()
        }
    }
}