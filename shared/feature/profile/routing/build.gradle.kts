plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.profile.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.profile.ui)
    api(projects.shared.feature.profile.domain)
    api(projects.shared.feature.profile.data)
}
