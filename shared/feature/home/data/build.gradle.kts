plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.home.data"
}

dependencies {
    implementation(projects.shared.feature.home.domain)
}
