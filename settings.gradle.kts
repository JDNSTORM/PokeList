pluginManagement {
    includeBuild("buildLogic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PokeList"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:data:network:network")
include(":core:data:network:networkData")
include(":core:data:network:networkKtorImpl")
include(":core:data:network:networkRetrofitImpl")
include(":core:data:local:local")
include(":core:data:local:localData")
