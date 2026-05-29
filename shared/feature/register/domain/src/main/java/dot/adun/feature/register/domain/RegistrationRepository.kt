package dot.adun.feature.register.domain

import dot.adun.feature.auth.domain.entity.AuthResult

interface RegistrationRepository {
    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): AuthResult
}
