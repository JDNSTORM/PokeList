plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.navorjames.pokelist.core.data.network.retrofit"

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
                api(projects.core.data.network.network)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
            }
        }

        commonTest {
            dependencies {

            }
        }

        androidMain {
            dependencies {
                implementation(libs.logging.interceptor)
                implementation(libs.retrofit)
                implementation(libs.retrofit.converter.serialization)
                implementation(libs.gson)
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