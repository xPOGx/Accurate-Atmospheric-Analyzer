plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // HILT
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.common"
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
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Firebase
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}