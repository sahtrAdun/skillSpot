plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.chat.ui"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)
    implementation(projects.shared.feature.chat.domain)
    implementation(projects.shared.feature.profile.domain)
}
