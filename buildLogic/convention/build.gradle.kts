plugins {
    `kotlin-dsl`
}
group = "com.navorjames.buildlogic"
//java {
//    sourceCompatibility = JavaVersion.VERSION_21
//    targetCompatibility = JavaVersion.VERSION_21
//}
//kotlin {
//    compilerOptions {
//        languageVersion = KotlinVersion.KOTLIN_2_3
//        jvmTarget = JvmTarget.JVM_21
//    }
//}
//tasks.compileKotlin {
//    compilerOptions {
//        languageVersion = KotlinVersion.KOTLIN_2_3
//    }
//}

dependencies {
    compileOnly(libs.androidToolsBuildGradle)
    compileOnly(libs.androidToolsCommon)
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.composeCompilerGradlePlugin)
    compileOnly(libs.kspGradlePlugin)
    compileOnly(libs.kmpGradlePlugin)
    compileOnly(libs.cmpGradlePlugin)
    compileOnly(libs.androidKmpLibraryGradlePlugin)
}

gradlePlugin {
    plugins {
        register("base"){
            id = "project.base"
            implementationClass = "BaseConventionPlugin"
        }
        register("kmp"){
            id = "project.kmp"
            implementationClass = "KmpConventionPlugin"
        }
    }
}