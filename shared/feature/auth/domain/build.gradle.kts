plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.auth.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
