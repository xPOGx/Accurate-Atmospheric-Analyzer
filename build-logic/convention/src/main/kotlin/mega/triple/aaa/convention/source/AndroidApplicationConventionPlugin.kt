package mega.triple.aaa.convention.source

import mega.triple.aaa.convention.core.addImplementation
import mega.triple.aaa.convention.core.app
import mega.triple.aaa.convention.core.configureKotlin
import mega.triple.aaa.convention.core.plugins
import mega.triple.aaa.convention.core.secretsGradle
import mega.triple.aaa.convention.core.versionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        plugins {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            apply("com.google.gms.google-services")
            apply("com.google.firebase.crashlytics")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        app {
            buildFeatures {
                buildConfig = true
            }

            defaultConfig {
                applicationId = versionCatalog.findVersion("applicationId").get().requiredVersion
                compileSdk = versionCatalog.findVersion("compileSdk").get().requiredVersion.toInt()
                minSdk = versionCatalog.findVersion("minSdk").get().requiredVersion.toInt()
                targetSdk = versionCatalog.findVersion("targetSdk").get().requiredVersion.toInt()

                versionCode =
                    versionCatalog.findVersion("versionCode").get().requiredVersion.toInt()
                versionName = versionCatalog.findVersion("versionName").get().requiredVersion
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
            buildTypes {
                release {
                    isMinifyEnabled = true
                }
                debug {
                    isMinifyEnabled = false
                }
            }
        }

        secretsGradle {
            propertiesFileName = "secrets.properties"
            defaultPropertiesFileName = "local.defaults.properties"
        }

        configureKotlin()

        dependencies {
            addImplementation(versionCatalog.findLibrary("androidx-core-ktx").get())
            addImplementation(versionCatalog.findLibrary("androidx-lifecycle-runtime-ktx").get())
            addImplementation(platform(versionCatalog.findLibrary("firebase-bom").get()))
            addImplementation(versionCatalog.findLibrary("firebase-analytics").get())
        }
    }
}
