import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("network")
}

dependencies {
    // Network
    implementation(libs.bundles.network)
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
}