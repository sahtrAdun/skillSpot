plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.core.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.domain)
}
