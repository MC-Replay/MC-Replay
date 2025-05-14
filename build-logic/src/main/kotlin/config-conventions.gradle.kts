plugins {
    id("base-conventions")
}

val rootProperties: Map<String, *> = project.rootProject.properties

@Suppress("UNCHECKED_CAST")
tasks {
    processResources {
        // plugin
        filesMatching(listOf("plugin.yml")) {
            expand(
                "version" to project.version,
                "author" to "MC-Replay"
            )
        }
    }
}