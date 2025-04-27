import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("common")
}

dependencies {
    ksp(libs.koin.compiler)
    // 3d party
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}