plugins {
    // CORE
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // HILT
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    // Gradle secrets
    alias(libs.plugins.secrets.gradle.plugin)
    // Firebase
    alias(libs.plugins.firebase)
    alias(libs.plugins.firebase.crashlytic)
}

android {
    with(libs) {
        namespace = versions.applicationId.get()
        compileSdk = versions.compileSdk.get().toInt()

        defaultConfig {
            applicationId = versions.applicationId.get()
            minSdk = versions.minSdk.get().toInt()
            //noinspection OldTargetApi
            targetSdk = versions.targetSdk.get().toInt()
            versionCode = versions.versionCode.get().toInt()
            versionName = versions.versionName.get()

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes {
            release {
                isMinifyEnabled = false
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(versions.javaVersion.get())
            targetCompatibility = JavaVersion.toVersion(versions.javaVersion.get())
        }
        kotlinOptions {
            jvmTarget = versions.javaVersion.get()
        }
        buildFeatures {
            buildConfig = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versions.kotlinCompilerExtensionVersion.get()
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
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
    // WorkManager
    implementation(libs.worker.hilt)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectFeatureMain.get()))
    implementation(project(libs.versions.projectFeatureSync.get()))
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}
