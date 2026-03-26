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
    // screens
    api(projects.shared.feature.home.routing)
    api(projects.shared.feature.search.routing)
}
