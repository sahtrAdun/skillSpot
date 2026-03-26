plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.core.ui"
}

dependencies {
    implementation(projects.shared.core.domain)
}
