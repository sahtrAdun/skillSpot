plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.home.routing"
}

dependencies {
    implementation(projects.shared.feature.authorized.routing)

    implementation(projects.shared.feature.search.routing)
    implementation(projects.shared.feature.settings.routing)
    implementation(projects.shared.feature.profile.routing)

    api(projects.shared.feature.home.ui)
    api(projects.shared.feature.home.domain)
    api(projects.shared.feature.home.data)
}
