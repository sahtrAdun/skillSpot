plugins {
    alias(libs.plugins.convention.shared)
}

android {
    namespace = "dot.adun.core.data"
}

dependencies {
    implementation(projects.shared.core.domain)

    api(libs.ktor.core)
    api(libs.ktor.android)
    api(libs.ktor.cio)
    api(libs.ktor.content.negotiation)
    api(libs.ktor.serialization.json)
    api(libs.ktor.logging)
    api(libs.ktor.auth)
    api(libs.ktor.okhttp)

    api(platform(libs.supabase.bom))
    api(libs.supabase.postgrest)
    api(libs.supabase.auth)
    api(libs.supabase.realtime)
    api(libs.supabase.storage)

    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")
}
