plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.register.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    implementation(projects.shared.feature.register.ui)
    implementation(projects.shared.feature.register.domain)
    implementation(projects.shared.feature.register.data)
}
