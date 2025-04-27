import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

buildscript {
    dependencies {
        classpath(libs.android.gradle.plugin)
    }
}

group = "${libs.versions.applicationId.get()}.convention"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.javaVersion.get()))
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.plugin)
    implementation(libs.secrets.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "mega.triple.aaa.convention.application"
            implementationClass =
                "mega.triple.aaa.convention.source.AndroidApplicationConventionPlugin"
        }
        register("androidDi") {
            id = "mega.triple.aaa.convention.di"
            implementationClass =
                "mega.triple.aaa.convention.source.AndroidDiConventionPlugin"
        }
        register("androidFeature") {
            id = "mega.triple.aaa.convention.feature"
            implementationClass =
                "mega.triple.aaa.convention.source.AndroidFeatureConventionPlugin"
        }
    }
}