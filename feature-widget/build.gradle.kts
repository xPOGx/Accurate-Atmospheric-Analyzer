plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Ksp
    alias(libs.plugins.ksp)
    // In kotlin 2+ need compose plugin
    alias(libs.plugins.jetbrains.kotlin.compose)
}

android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.widget"
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
    implementation(libs.koin.androidx.compose)
    ksp(libs.koin.compiler)
    // Widget
    implementation(libs.bundles.widget)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectDomain.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
    implementation(project(libs.versions.projectCoreStrings.get()))
    implementation(project(libs.versions.projectFeatureMain.get()))
}