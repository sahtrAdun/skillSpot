import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.project
import org.gradle.plugin.use.PluginDependency
import java.util.Optional

fun Project.libs(): VersionCatalog = extensions.findByType(VersionCatalogsExtension::class.java)?.named("libs") ?: error("Version catalog 'libs' not found")

fun Project.applyPlugin(libs: VersionCatalog, alias: String) {
    libs.findPlugin(alias).ifPresent { plugin ->
        pluginManager.apply(plugin.get().pluginId)
    }
}

private fun DependencyHandler.addPlugin(tag: String, libs: VersionCatalog, alias: String) {
    val library = libs.findLibrary(alias)
    if (library.isPresent) {
        add(tag, library.get())
    } else {
        throw IllegalArgumentException("'$alias' not found in [$tag : $libs]")
    }
}

fun DependencyHandler.implementation(libs: VersionCatalog, alias: String) {
    addPlugin("implementation", libs, alias)
}
fun DependencyHandler.debug(libs: VersionCatalog, dependency: String) = add("debugImplementation", libs.findLibrary(dependency).get())
fun DependencyHandler.api(libs: VersionCatalog, dependency: String) = add("api", libs.findLibrary(dependency).get())
fun DependencyHandler.ksp(libs: VersionCatalog, dependency: String) = add("ksp", libs.findLibrary(dependency).get())
fun DependencyHandler.compileOnly(libs: VersionCatalog, dependency: String) = add("compileOnly", libs.findLibrary(dependency).get())
fun DependencyHandler.runtimeOnly(libs: VersionCatalog, dependency: String) = add("runtimeOnly", libs.findLibrary(dependency).get())
fun DependencyHandler.testImplementation(libs: VersionCatalog, dependency: String) = add("testImplementation", libs.findLibrary(dependency).get())
fun DependencyHandler.androidTestImplementation(libs: VersionCatalog, dependency: String) = add("androidTestImplementation", libs.findLibrary(dependency).get())
fun DependencyHandler.implementation(dependency: Optional<out Provider<out ExternalModuleDependency>>) {
    dependency.ifPresent { add("implementation", it.get()) }
}
fun DependencyHandler.implementationProject(path: String) {
    add("implementation", project(path))
}
