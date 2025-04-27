import mega.triple.aaa.convention.core.namespace

plugins {
    id("mega.triple.aaa.convention.feature")
    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace("navigation")
}