plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)
    implementation(projects.shared.core.domain)
    // app
    implementation(projects.shared.feature.authorized.routing)
    implementation(projects.shared.feature.home.routing)
    implementation(projects.shared.feature.auth.routing)
    implementation(projects.shared.feature.search.routing)
    implementation(projects.shared.feature.settings.routing)
}
