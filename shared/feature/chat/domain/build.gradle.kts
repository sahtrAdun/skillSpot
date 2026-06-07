plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.chat.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
