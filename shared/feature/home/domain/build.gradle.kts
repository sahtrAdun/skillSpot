plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.home.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
