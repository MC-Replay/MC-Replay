import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    id("minecraft-conventions")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    api(projects.api)
    api(projects.nmsCommon)

    paperweight.paperDevBundle(libs.versions.minecraft.v121R1)
}

// Use the Mojang production reobfuscation configuration
paperweight.reobfArtifactConfiguration.set(ReobfArtifactConfiguration.MOJANG_PRODUCTION)