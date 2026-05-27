package dot.adun.feature.register.data

import dot.adun.feature.auth.data.api.AuthApi
import dot.adun.feature.auth.domain.entity.AuthResult
import dot.adun.feature.register.domain.RegistrationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegistrationDataRepository @Inject constructor(
    private val api: AuthApi
) : RegistrationRepository {
    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): AuthResult = api.signUpWithEmail(email, password)
}
