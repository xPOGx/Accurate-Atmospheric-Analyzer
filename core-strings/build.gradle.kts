plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.strings"
        compileSdk = versions.compileSdk.get().toInt()

        defaultConfig {
            minSdk = versions.minSdk.get().toInt()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(versions.javaVersion.get())
            targetCompatibility = JavaVersion.toVersion(versions.javaVersion.get())
        }
        kotlinOptions {
            jvmTarget = versions.javaVersion.get()
        }
    }
}