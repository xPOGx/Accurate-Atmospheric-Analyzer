package mega.triple.aaa.convention.core

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.google.android.libraries.mapsplatform.secrets_gradle_plugin.SecretsPluginExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val Project.versionCatalog
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


fun Project.app(block: ApplicationExtension.() -> Unit) {
    this.extensions.configure<ApplicationExtension> {
        this.block()
    }
}

fun Project.namespace(namespace: String) {
    this.extensions.configure<LibraryExtension> {
        this.namespace =
            "${versionCatalog.findVersion("applicationId").get().requiredVersion}.$namespace"
    }
}

fun Project.lib(block: LibraryExtension.() -> Unit) {
    this.extensions.configure<LibraryExtension> {
        this.block()
    }
}

fun Project.ksp(block: KspExtension.() -> Unit) {
    this.extensions.configure<KspExtension> {
        this.block()
    }
}

fun Project.plugins(block: PluginManager.() -> Unit) {
    this.pluginManager.block()
}

fun Project.secretsGradle(block: SecretsPluginExtension.() -> Unit) {
    val secretsScope = extensions.getByType<SecretsPluginExtension>()
    secretsScope.block()
}

fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(versionCatalog.findVersion("javaVersion").get().toString()))
        }
    }
}
