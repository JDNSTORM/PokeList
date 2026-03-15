package com.navorjames.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal object Extensions {
    val Project.libs: VersionCatalog
        get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

    operator fun VersionCatalog.get(
        libraryAlias: String
    ): Provider<MinimalExternalModuleDependency> = findLibrary(libraryAlias).get()

    val VersionCatalog.androidCompileSdk: Int
        get() = findVersion("android.compileSdk").get().toString().toInt()

    val VersionCatalog.androidMinSdk: Int
        get() = findVersion("android.minSdk").get().toString().toInt()

    val VersionCatalog.androidTargetSdk: Int
        get() = findVersion("android.targetSdk").get().toString().toInt()

    val VersionCatalog.jvmToolchain: Int
        get() = findVersion("jvmToolchain").get().toString().toInt()
}