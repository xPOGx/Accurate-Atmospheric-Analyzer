import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("widget")
}

dependencies {
    // Widget
    implementation(libs.bundles.widget)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectDomain.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
    implementation(project(libs.versions.projectCoreStrings.get()))
    implementation(project(libs.versions.projectFeatureMain.get()))
}