package mega.triple.aaa.convention.core

import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.addImplementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

internal fun DependencyHandlerScope.addDebugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

internal fun DependencyHandlerScope.addKsp(dependencyNotation: Any): Dependency? =
    add("ksp", dependencyNotation)
