plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.auth.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    implementation(projects.shared.feature.login.routing)
    implementation(projects.shared.feature.register.routing)

    api(projects.shared.feature.auth.ui)
    api(projects.shared.feature.auth.domain)
    api(projects.shared.feature.auth.data)
}
