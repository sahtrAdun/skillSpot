plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.profile.data"
}

dependencies {
    implementation(projects.shared.core.data)
    implementation(projects.shared.feature.profile.domain)
}
