plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.authorized.domain"
}

dependencies {
    api(projects.shared.core.domain)
    api(projects.shared.feature.profile.domain)
}
