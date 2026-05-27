plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.register.data"
}

dependencies {
    implementation(projects.shared.core.data)
    implementation(projects.shared.feature.register.domain)
    implementation(projects.shared.feature.auth.data)
}
