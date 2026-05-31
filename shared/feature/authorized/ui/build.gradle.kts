plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.authorized.ui"
}

dependencies {
    api(projects.shared.core.ui)
    api(projects.shared.core.routing)

    api(projects.shared.feature.authorized.domain)
    implementation(projects.shared.feature.profile.domain)
}
