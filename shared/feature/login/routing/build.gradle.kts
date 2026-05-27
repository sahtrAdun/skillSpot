plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.login.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.login.ui)
    api(projects.shared.feature.login.domain)
    api(projects.shared.feature.login.data)
}
