plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.settings.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.settings.ui)
    api(projects.shared.feature.settings.domain)
    api(projects.shared.feature.settings.data)
}