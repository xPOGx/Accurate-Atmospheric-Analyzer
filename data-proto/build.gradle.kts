plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Datastore
    alias(libs.plugins.protobuf)
    // Ksp
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
        ksp {
            arg("KOIN_CONFIG_CHECK", "true")
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
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotatinos)
    ksp(libs.koin.compiler)
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
