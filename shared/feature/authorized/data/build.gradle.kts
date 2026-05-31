plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.authorized.data"
}

dependencies {
    api(projects.shared.core.data)
    api(projects.shared.feature.authorized.domain)
}

