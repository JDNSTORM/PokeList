package com.navorjames.buildlogic.convention

internal object Plugins {
    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIB = "com.android.library"
    const val ANDROID_KMP_LIB = "com.android.kotlin.multiplatform.library"
    const val ANDROID_TEST = "com.android.test"
    const val ANDROID_BASE = "com.android.base"
    const val JVM_LIB = "org.jetbrains.kotlin.jvm"
    const val KMP = "org.jetbrains.kotlin.multiplatform"
    const val KSP = "com.google.devtools.ksp"
    const val COMPOSE = "org.jetbrains.kotlin.plugin.compose"
    const val COMPOSE_MULTIPLATFORM = "org.jetbrains.compose"
    const val SERIALIZATION = "org.jetbrains.kotlin.plugin.serialization"
}