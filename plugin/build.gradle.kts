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