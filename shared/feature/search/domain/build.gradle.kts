plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.search.domain"
}

dependencies {
    api(projects.shared.core.domain)
}
