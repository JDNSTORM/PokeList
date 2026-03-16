plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidxRoom)
}

kotlin {
    android {
        namespace = "com.navorjames.pokelist.core.data.local"

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
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.paging.common.ktx)
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
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    kspCommonMainMetadata(libs.androidx.room.compiler)
    kspAndroidMain(libs.androidx.room.compiler)
}