plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.auth.data"
}

dependencies {
    implementation(projects.shared.core.data)
    implementation(projects.shared.feature.auth.domain)
}