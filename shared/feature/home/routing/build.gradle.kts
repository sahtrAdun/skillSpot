plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.home.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    implementation(projects.shared.feature.search.routing)

    api(projects.shared.feature.home.ui)
    api(projects.shared.feature.home.domain)
    api(projects.shared.feature.home.data)
}
