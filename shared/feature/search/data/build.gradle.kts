plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.feature.search.data"
}

dependencies {
    implementation(projects.shared.feature.search.domain)
}
