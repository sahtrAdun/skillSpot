plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.home.ui"
}

dependencies {
    implementation(projects.shared.feature.authorized.ui)

    implementation(projects.shared.feature.home.domain)
    implementation(projects.shared.feature.profile.domain)
}
