plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.core.domain"
}

dependencies {
    api(projects.shared.common.resources)
    implementation(libs.ktor.core)
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.auth)
}
