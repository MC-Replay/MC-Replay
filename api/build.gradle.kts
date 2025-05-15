import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("minecraft-conventions")
    id("shadow-conventions")
}

dependencies {
    compileOnlyApi(libs.packetlib)
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("MC-Replay-API")
}