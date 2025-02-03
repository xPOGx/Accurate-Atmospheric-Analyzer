plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.navigation"
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

dependencies {
    // CORE
    implementation(libs.androidx.core.ktx)
    // Serialization
    implementation(libs.kotlinx.serialization.json)
}