plugins {
    // CORE
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // In kotlin 2+ need compose plugin
    alias(libs.plugins.jetbrains.kotlin.compose)
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // HILT
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    // 3d party
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
            compose = true
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
    implementation(libs.androidx.activity.compose)
    // COMPOSE
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // WorkManager
    implementation(libs.worker.lib)
    implementation(libs.worker.startup)
    implementation(libs.worker.hilt)
    ksp(libs.worker.hiltCompiler)
    // Network
    implementation(libs.io.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)
    // HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Datastore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.protobuf.javalite)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    // Modules
    implementation(project(libs.versions.projectCommon.get()))
    implementation(project(libs.versions.projectDataNetwork.get()))
    implementation(project(libs.versions.projectDataProto.get()))
    implementation(project(libs.versions.projectDataPreference.get()))
    implementation(project(libs.versions.projectDataLocal.get()))
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}
