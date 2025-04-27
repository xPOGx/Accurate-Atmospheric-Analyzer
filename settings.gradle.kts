@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
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
include(":data-network")
include(":data-proto")
include(":data-preference")
include(":data-local")
include(":domain")
include(":core-common")
include(":core-ui")
include(":core-strings")
include(":core-navigation")
include(":feature-main")
include(":feature-home")
include(":feature-search")
include(":feature-settings")
include(":feature-sync")
include(":feature-widget")
