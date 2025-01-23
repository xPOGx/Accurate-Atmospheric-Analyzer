plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Ksp
    alias(libs.plugins.ksp)
}


android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.domain"
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
    // Proto
    implementation(libs.protobuf.javalite)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectDataNetwork.get()))
    implementation(project(libs.versions.projectDataProto.get()))
    implementation(project(libs.versions.projectDataPreference.get()))
    implementation(project(libs.versions.projectDataLocal.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
}