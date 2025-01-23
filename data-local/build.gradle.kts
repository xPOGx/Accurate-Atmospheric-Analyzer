plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Ksp
    alias(libs.plugins.ksp)
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.local"
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
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotatinos)
    ksp(libs.koin.compiler)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Serialization
    implementation(libs.io.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)
}