// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // Upgrade to a higher KGP version instead of built-in Kotlin
        // https://developer.android.com/build/releases/agp-9-0-0-release-notes?utm_source=android-studio-app&utm_medium=app#runtime-dependency-on-kotlin-gradle-plugin-upgrade
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.kspGradlePlugin)
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinPluginSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.androidLint) apply false
}
