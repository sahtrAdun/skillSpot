plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.register.ui"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)
    implementation(projects.shared.feature.register.domain)
}
