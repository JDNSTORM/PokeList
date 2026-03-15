import com.navorjames.buildlogic.convention.Extensions.get
import com.navorjames.buildlogic.convention.Extensions.jvmToolchain
import com.navorjames.buildlogic.convention.Extensions.libs
import com.navorjames.buildlogic.convention.Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A Gradle convention plugin for setting up Kotlin Multiplatform projects.
 *
 * This plugin applies the necessary Kotlin Multiplatform plugin and configures
 * common dependencies for main and test source sets. It also sets the JVM toolchain.
 *
 * This plugin serves as a Base Kotlin Multiplatform Template. Platform Targets should be applied on top of it.
 */
class KmpConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply(Plugins.KMP)
            }

            configure<KotlinMultiplatformExtension> {
                jvmToolchain(libs.jvmToolchain)
                sourceSets.apply {
                    commonMain {
                        dependencies {
                            implementation(libs["kotlin.stdlib"])
                        }
                    }

                    commonTest {
                        dependencies {
                            implementation(libs["kotlin.test"])
                            implementation(libs["assertk"])
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val PLUGIN_ID = "project.kmp"
    }
}