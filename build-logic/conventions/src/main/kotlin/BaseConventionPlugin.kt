import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class BaseConventionPlugin : Plugin<Project> {
    private fun setProjectConfig(project: Project) = with(project) {
        android().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            configureKotlinJvm()
        }
    }

    private fun Project.configureKotlinJvm() {
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    protected fun Project.android(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }

    abstract fun applyPlugins(project: Project)
    abstract fun applyDependencies(project: Project)

    override fun apply(project: Project): Unit = kotlin.with(project) {
        applyPlugins(project)
        setProjectConfig(project)
        applyDependencies(project)
    }
}