import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("domain")
}

dependencies {
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