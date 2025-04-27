import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("home")
}

dependencies {
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectDomain.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
    implementation(project(libs.versions.projectCoreStrings.get()))
    implementation(project(libs.versions.projectCoreNavigation.get()))
}