import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AppConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        applyPlugins(project)
        applyDependencies(project)
    }

    private fun applyPlugins(project: Project): Unit = with(project) {
        val libs = libs()

        project.pluginManager.apply {
            applyPlugin(libs, "android-application")
            applyPlugin(libs, "kotlin-compose")
            applyPlugin(libs, "serialization-json")
            applyPlugin(libs, "dagger-hilt")
            applyPlugin(libs, "ksp")
        }
    }

    private fun applyDependencies(project: Project): Unit = with(project) {
        val libs = libs()

        dependencies {
            implementation(libs, "compose-bom")
            implementation(libs, "compose-ui")
            implementation(libs, "compose-ui-graphics")
            implementation(libs, "compose-animations")
            implementation(libs, "compose-ui-tooling-preview")
            implementation(libs, "compose-foundation")
            implementation(libs, "compose-material3")
            implementation(libs, "compose-activity")
            implementation(libs, "compose-lifecycle-vm")
            implementation(libs, "compose-navigation-core")
            implementation(libs, "compose-navigation-ui")
            implementation(libs, "compose-navigation-viewmodel")
            implementation(libs, "compose-navigation-adaptive")
            implementation(libs, "compose-coil")
            implementation(libs, "compose-haze")
            debug(libs, "compose-ui-tooling")

            implementation(libs, "kotlinx-serialization-json")
            implementation(libs, "log-napier")
            implementation(libs, "javapoet")
            implementation(libs, "hilt")
            implementation(libs, "hilt-compose-navigation")
            ksp(libs, "hilt-compiler")
        }
    }
}