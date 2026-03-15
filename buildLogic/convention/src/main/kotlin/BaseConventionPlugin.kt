import org.gradle.api.Plugin
import org.gradle.api.Project

class BaseConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {}

    companion object {
        const val PLUGIN_ID = "project.base"
    }
}