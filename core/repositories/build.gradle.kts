plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.navorjames.pokelist.core.repositories"

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
                api(projects.core.data.local.localData)
                implementation(projects.core.data.local.local)
                implementation(projects.core.data.network.network)
                implementation(libs.androidx.paging.runtime.ktx)
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