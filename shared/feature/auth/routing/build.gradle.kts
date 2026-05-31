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
    implementation(projects.shared.feature.profile.routing)

    implementation(projects.shared.feature.auth.ui)
    implementation(projects.shared.feature.auth.domain)
    implementation(projects.shared.feature.auth.data)
}
