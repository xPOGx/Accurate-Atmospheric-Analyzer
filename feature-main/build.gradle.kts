import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("main")
}

dependencies {
    // CORE
    implementation(libs.androidx.activity.compose)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectCoreStrings.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
    implementation(project(libs.versions.projectCoreNavigation.get()))
    implementation(project(libs.versions.projectDomain.get()))
    implementation(project(libs.versions.projectFeatureHome.get()))
    implementation(project(libs.versions.projectFeatureSearch.get()))
    implementation(project(libs.versions.projectFeatureSettings.get()))
}