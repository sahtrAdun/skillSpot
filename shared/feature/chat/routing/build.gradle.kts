plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.chat.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.chat.ui)
    api(projects.shared.feature.chat.domain)
    api(projects.shared.feature.chat.data)
}
