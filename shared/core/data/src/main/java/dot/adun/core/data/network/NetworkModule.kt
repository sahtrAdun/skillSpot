package dot.adun.core.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds
import io.github.jan.supabase.auth.Auth as SupabaseAuth

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://lkaqsjcbokvkalesftqj.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxrYXFzamNib2t2a2FsZXNmdHFqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg3NTE0NTUsImV4cCI6MjA5NDMyNzQ1NX0.sAE0AL5VtJ_SfpSm7_DKLCGAoUGv0Pm3AzU1rLXs11c"
        ) {
            defaultSerializer = KotlinXSerializer(Json { ignoreUnknownKeys = true })
            httpEngine = OkHttp.create {
                config {
                    connectTimeout(30.seconds)
                    readTimeout(30.seconds)
                    writeTimeout(30.seconds)

                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            install(SupabaseAuth) {
                alwaysAutoRefresh = true
            }
            install(Postgrest)
            install(Realtime)
        }
    }

    @Provides
    @Singleton
    fun provideKtorClient(
        supabase: SupabaseClient
    ): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json { ignoreUnknownKeys = true }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.i(tag = "HTTP_CLIENT", message = message)
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val session = supabase.auth.currentSessionOrNull()
                        session?.let { BearerTokens(it.accessToken, it.refreshToken) }
                    }
                    refreshTokens {
                        try {
                            supabase.auth.refreshCurrentSession()
                        } catch (_: Exception) {
                            return@refreshTokens null
                        }

                        val updatedSession = supabase.auth.currentSessionOrNull()
                        if (updatedSession != null) {
                            BearerTokens(
                                accessToken = updatedSession.accessToken,
                                refreshToken = updatedSession.refreshToken
                            )
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }
}
