plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.settings.data"
}

dependencies {
    implementation(projects.shared.feature.settings.domain)
}
