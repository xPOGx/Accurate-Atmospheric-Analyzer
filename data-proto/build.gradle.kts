plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Datastore
    alias(libs.plugins.protobuf)
    // HILT
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
}


android {
    with(libs) {
        namespace = "${versions.applicationId.get()}.proto"
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
    // Datastore
    implementation(libs.androidx.datastore)
    // Proto
    implementation(libs.protobuf.javalite)
    // HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
