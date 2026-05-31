plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.authorized.routing"
}

dependencies {
    api(projects.shared.core.ui)
    api(projects.shared.core.routing)

    api(projects.shared.feature.authorized.ui)
    api(projects.shared.feature.authorized.domain)
    api(projects.shared.feature.authorized.data)
}
