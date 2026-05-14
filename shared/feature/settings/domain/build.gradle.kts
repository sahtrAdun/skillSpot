plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.settings.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
