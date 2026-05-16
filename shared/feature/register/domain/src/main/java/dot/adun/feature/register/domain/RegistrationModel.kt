package dot.adun.feature.register.domain

import dot.adun.feature.auth.domain.AuthResult
import javax.inject.Inject

class RegistrationModel @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): AuthResult = repository.signUpWithEmail(email, password)
}
