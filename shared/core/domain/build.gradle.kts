plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.core.domain"
}

dependencies {
    api(projects.shared.common.resources)
}
