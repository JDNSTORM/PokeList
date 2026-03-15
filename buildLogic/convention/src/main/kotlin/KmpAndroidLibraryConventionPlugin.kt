import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.navorjames.buildlogic.convention.Extensions.androidCompileSdk
import com.navorjames.buildlogic.convention.Extensions.androidMinSdk
import com.navorjames.buildlogic.convention.Extensions.libs
import com.navorjames.buildlogic.convention.Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A Gradle convention plugin for configuring a Kotlin Multiplatform project with an Android library target.
 *
 * This plugin performs the following configurations:
 * 1. Applies the [KmpConventionPlugin] and the Android Kotlin Multiplatform library plugin.
 * 2. Configures the `androidLibrary` target with `compileSdk` and `minSdk` versions defined in the version catalog.
 * 3. Automatically detects and configures Proguard/R8 optimization rules by checking for the existence of:
 *    - `proguard-rules.pro`
 *    - `proguard-rules.android.pro`
 *    - `consumer-rules.pro`
 *    - `consumer-rules.android.pro`
 */
class KmpAndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply(KmpConventionPlugin::class.java)
                apply(Plugins.ANDROID_KMP_LIB)
            }

            configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                    compileSdk = libs.androidCompileSdk
                    minSdk = libs.androidMinSdk

                    optimization {
                        val proguardFiles = listOfNotNull(
                            file("proguard-rules.pro").takeIf { it.exists() },
                            file("proguard-rules.android.pro").takeIf { it.exists() },
                            file("consumer-rules.pro").takeIf { it.exists() },
                            file("consumer-rules.android.pro").takeIf { it.exists() },
                        )
                        if (proguardFiles.isEmpty()) return@optimization
                        println("$path: ${proguardFiles.size} Android Proguard Files Found")
                        consumerKeepRules.publish = true
                        consumerKeepRules.files.addAll(proguardFiles)
                    }
                }
            }
        }
    }

    companion object {
        const val PLUGIN_ID = "project.kmp.android.library"
    }
}