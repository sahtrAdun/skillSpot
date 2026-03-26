plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.core.data"
}

dependencies {
    implementation(projects.shared.core.domain)
}
