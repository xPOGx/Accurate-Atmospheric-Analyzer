package mega.triple.aaa.convention.source

import mega.triple.aaa.convention.core.addDebugImplementation
import mega.triple.aaa.convention.core.addImplementation
import mega.triple.aaa.convention.core.configureKotlin
import mega.triple.aaa.convention.core.lib
import mega.triple.aaa.convention.core.plugins
import mega.triple.aaa.convention.core.versionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        plugins {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        lib {
            defaultConfig {
                compileSdk = versionCatalog.findVersion("compileSdk").get().requiredVersion.toInt()
                minSdk = versionCatalog.findVersion("minSdk").get().requiredVersion.toInt()
            }
            compileOptions {
                sourceCompatibility = JavaVersion.toVersion(
                    versionCatalog.findVersion("javaVersion").get().requiredVersion
                )
                targetCompatibility = JavaVersion.toVersion(
                    versionCatalog.findVersion("javaVersion").get().requiredVersion
                )
            }
            composeOptions {
                kotlinCompilerExtensionVersion =
                    versionCatalog.findVersion("kotlinCompilerExtensionVersion").get().requiredVersion
            }
            packaging {
                resources {
                    excludes += listOf(
                        "META-INF/LICENSE.md",
                        "META-INF/LICENSE-notice.md",
                    )
                }
            }
        }

        configureKotlin()

        dependencies {
            addImplementation(versionCatalog.findLibrary("androidx-core-ktx").get())
            addImplementation(versionCatalog.findLibrary("androidx-lifecycle-runtime-ktx").get())
            addImplementation(versionCatalog.findLibrary("kotlinx-serialization-json").get())
            addImplementation(platform(versionCatalog.findLibrary("androidx-compose-bom").get()))
            addImplementation(versionCatalog.findLibrary("androidx-ui").get())
            addImplementation(versionCatalog.findLibrary("androidx-ui-graphics").get())
            addImplementation(versionCatalog.findLibrary("androidx-ui-tooling-preview").get())
            addImplementation(versionCatalog.findLibrary("androidx-material3").get())
            addImplementation(versionCatalog.findLibrary("androidx-navigation-compose").get())
            addDebugImplementation(versionCatalog.findLibrary("androidx-ui-tooling").get())
            addDebugImplementation(versionCatalog.findLibrary("androidx-ui-test-manifest").get())
        }
    }
}
