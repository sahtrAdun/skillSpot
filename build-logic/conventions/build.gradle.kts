plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("shared") {
            id = libs.plugins.convention.shared.get().pluginId
            implementationClass = "SharedConventionPlugin"
        }

        register("shared-common") {
            id = libs.plugins.convention.common.get().pluginId
            implementationClass = "CommonConventionPlugin"
        }

        register("compose") {
            id = libs.plugins.convention.compose.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }

        register("app") {
            id = libs.plugins.convention.app.get().pluginId
            implementationClass = "AppConventionPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.gradleplugin.android)
    compileOnly(libs.gradleplugin.kotlin)
    compileOnly(libs.gradleplugin.compose)
    compileOnly(libs.gradleplugin.composeCompiler)
}
