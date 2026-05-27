plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.login.data"
}

dependencies {
    implementation(projects.shared.core.data)
    implementation(projects.shared.feature.login.domain)
    implementation(projects.shared.feature.auth.data)
}