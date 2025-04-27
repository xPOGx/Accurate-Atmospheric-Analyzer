import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // Datastore
    alias(libs.plugins.protobuf)
}

android {
    namespace("proto")
}

dependencies {
    // Datastore
    implementation(libs.androidx.datastore)
    // Proto
    implementation(libs.protobuf.javalite)
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
