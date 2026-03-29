import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin : BaseConventionPlugin() {
    override fun apply(project: Project): Unit = with(project) {
        super.apply(project)

        android {
            apply {
                buildFeatures {
                    compose = true
                }
            }
        }
    }

    override fun applyPlugins(project: Project): Unit = with(project) {
        val libs = libs()

        project.pluginManager.apply {
            applyPlugin(libs, "android-library")
            applyPlugin(libs, "kotlin-compose")
            applyPlugin(libs, "serialization-json")
            applyPlugin(libs, "dagger-hilt")
            applyPlugin(libs, "ksp")
        }
    }

    override fun applyDependencies(project: Project): Unit = with(project) {
        val libs = libs()

        dependencies {
            implementation(libs, "compose-bom")
            implementation(libs, "compose-ui")
            implementation(libs, "compose-icons")
            implementation(libs, "compose-ui-graphics")
            implementation(libs, "compose-animations")
            implementation(libs, "compose-ui-tooling-preview")
            implementation(libs, "compose-foundation")
            implementation(libs, "compose-material3")
            implementation(libs, "compose-activity")
            implementation(libs, "compose-lifecycle-vm")
            implementation(libs, "compose-coil")
            implementation(libs, "compose-navigation-core")
            implementation(libs, "compose-navigation-ui")
            implementation(libs, "compose-navigation-viewmodel")
            implementation(libs, "compose-navigation-adaptive")
            implementation(libs, "compose-cloudy")
            //implementation(libs, "compose-shimmer")
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