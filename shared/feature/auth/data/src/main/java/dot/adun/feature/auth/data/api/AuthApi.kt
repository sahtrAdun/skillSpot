package dot.adun.feature.auth.data.api

import dot.adun.feature.auth.domain.entity.AuthResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApi @Inject constructor(
    private val supabase: SupabaseClient
) {
    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): AuthResult = withContext(Dispatchers.IO) {
        supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        AuthResult.Success
    }

    suspend fun loginWithEmail(
        email: String,
        password: String
    ): AuthResult = withContext(Dispatchers.IO) {
        try {
            supabase.auth.signOut()
        } catch (_: Exception) {
            /* do nothing */
        } finally {
            supabase.auth.clearSession()
        }

        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        AuthResult.Success
    }
}
