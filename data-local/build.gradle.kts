import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    id("mega.triple.aaa.convention.di")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("local")
}

dependencies {
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Serialization
    implementation(libs.io.ktor.serialization.json)
}