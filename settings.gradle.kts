@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Accurate Atmospheric Analyzer"
include(":app")
include(":core-common")
include(":data-network")
include(":data-proto")
include(":data-preference")
include(":data-local")
include(":domain")
include(":core-ui")
include(":core-strings")
include(":feature-main")
include(":feature-home")
include(":feature-search")
include(":feature-settings")
include(":feature-sync")
