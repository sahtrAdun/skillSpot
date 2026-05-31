plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.profile.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
