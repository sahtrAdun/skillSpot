plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.chat.data"
}

dependencies {
    api(projects.shared.core.data)
    implementation(projects.shared.feature.chat.domain)
}
