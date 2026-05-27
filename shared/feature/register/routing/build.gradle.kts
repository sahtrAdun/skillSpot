plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "dot.adun.feature.register.routing"
}

dependencies {
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.routing)

    api(projects.shared.feature.register.ui)
    api(projects.shared.feature.register.domain)
    api(projects.shared.feature.register.data)
}
