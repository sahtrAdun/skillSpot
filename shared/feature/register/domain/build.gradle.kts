plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.register.domain"
}

dependencies {
    api(projects.shared.core.domain)
    api(projects.shared.feature.auth.domain)
}
