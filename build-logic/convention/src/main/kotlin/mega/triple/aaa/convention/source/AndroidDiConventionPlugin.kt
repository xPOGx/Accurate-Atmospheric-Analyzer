package mega.triple.aaa.convention.source

import mega.triple.aaa.convention.core.addImplementation
import mega.triple.aaa.convention.core.addKsp
import mega.triple.aaa.convention.core.ksp
import mega.triple.aaa.convention.core.plugins
import mega.triple.aaa.convention.core.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        plugins {
            apply("com.google.devtools.ksp")
        }

        ksp {
            arg("KOIN_CONFIG_CHECK", "true")
        }

        dependencies {
            addImplementation(versionCatalog.findLibrary("koin-android").get())
            addImplementation(versionCatalog.findLibrary("koin-androidx-worker").get())
            addImplementation(versionCatalog.findLibrary("koin-androidx-compose").get())
            addImplementation(versionCatalog.findLibrary("koin-annotatinos").get())
            addKsp(versionCatalog.findLibrary("koin-compiler").get())
        }
    }
}
