plugins {
    id("minecraft-conventions")
}

dependencies {
    api(projects.api)
    api(projects.mappings)
    api(projects.nmsCommon)

    compileOnlyApi(libs.odalitamenus)
    compileOnlyApi(libs.tritejection)
}