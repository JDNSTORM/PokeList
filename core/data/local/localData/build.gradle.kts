plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinPluginSerialization)
}

kotlin {
    android {
        namespace = "com.navorjames.pokelist.core.data.local.data"

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.data.network.networkData)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.androidx.room.common)
            }
        }

        commonTest {
            dependencies {

            }
        }

        androidMain {
            dependencies {

            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }
    }
}
dependencies {
    kspCommonMainMetadata(libs.androidx.room.compiler)
    kspAndroid(libs.androidx.room.compiler)
}