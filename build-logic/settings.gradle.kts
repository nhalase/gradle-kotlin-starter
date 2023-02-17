pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin.plugin.serialization")) {
                useVersion(gradle.rootProject.extra["kotlinVersion"] as String)
            }
        }
    }
}

rootProject.name = "gradle-starter-kotlin-build-logic"
