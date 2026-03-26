plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.search.ui"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)
    implementation(projects.shared.feature.search.domain)
}
