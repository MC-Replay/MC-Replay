import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("config-conventions")
    id("minecraft-conventions")
    id("shadow-conventions")
}

dependencies {
    api(projects.common)
    api(projects.recording)
    api(projects.replay)

    api(projects.nmsV121R1)
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("MC-Replay")
}