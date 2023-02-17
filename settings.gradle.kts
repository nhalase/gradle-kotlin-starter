pluginManagement {
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion(gradle.rootProject.extra["kotlinVersion"] as String)
            }
        }
    }
}

// https://docs.gradle.org/7.3/userguide/dependency_management.html#sub:centralized-repository-declaration
dependencyResolutionManagement {
    // By default, repositories declared by a project will override whatever is declared in settings.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "gradle-kotlin-starter"
include("kotlin-ext", "lib", "microservice", "app")
