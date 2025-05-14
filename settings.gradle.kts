enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "MC-Replay"

include(":api")
include(":common")
include(":mappings")

include(":nms-common")
include(":nms-v1_21_R1")

include(":plugin")
include(":replay")
include(":recording")

// Specify project dirs
project(":nms-common").projectDir = file("nms/nms-common")
project(":nms-v1_21_R1").projectDir = file("nms/v1_21_R1")
