plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.search.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.search.ui)
    api(projects.shared.feature.search.domain)
    api(projects.shared.feature.search.data)
}