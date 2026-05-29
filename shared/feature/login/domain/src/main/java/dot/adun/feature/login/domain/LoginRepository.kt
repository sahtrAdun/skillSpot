package dot.adun.feature.login.domain

import dot.adun.feature.auth.domain.entity.AuthResult

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String): AuthResult
}
