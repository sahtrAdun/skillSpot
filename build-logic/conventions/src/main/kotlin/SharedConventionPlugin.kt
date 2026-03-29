import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SharedConventionPlugin : BaseConventionPlugin() {
    override fun applyPlugins(project: Project): Unit = with(project) {
        val libs = libs()

        project.pluginManager.apply {
            applyPlugin(libs, "android-library")
            applyPlugin(libs, "serialization-json")
            applyPlugin(libs, "dagger-hilt")
            applyPlugin(libs, "ksp")
        }
    }

    override fun applyDependencies(project: Project): Unit = with(project) {
        val libs = libs()

        dependencies {
            implementation(libs, "kotlinx-serialization-json")
            implementation(libs, "log-napier")
            implementation(libs, "javapoet")
            implementation(libs, "hilt")
            ksp(libs, "hilt-compiler")
        }
    }
}
