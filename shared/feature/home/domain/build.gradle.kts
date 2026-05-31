plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.home.domain"
}

dependencies {
    api(projects.shared.feature.authorized.domain)
    api(projects.shared.feature.settings.domain)
}
