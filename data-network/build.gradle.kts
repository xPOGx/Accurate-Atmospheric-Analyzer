plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // Ksp
    alias(libs.plugins.ksp)
}


android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.network"
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
        ksp {
            arg("KOIN_CONFIG_CHECK", "true")
        }
    }
}

dependencies {
    // CORE
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Network
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization.json)
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotatinos)
    ksp(libs.koin.compiler)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
}