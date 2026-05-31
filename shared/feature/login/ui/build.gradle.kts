plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.login.ui"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)
    implementation(projects.shared.feature.login.domain)
    implementation(projects.shared.feature.auth.ui)
}
