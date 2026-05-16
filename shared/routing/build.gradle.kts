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
    implementation(projects.shared.feature.home.routing)
    implementation(projects.shared.feature.auth.routing)
}
