package dot.adun.feature.login.data

import dot.adun.feature.auth.data.api.AuthApi
import dot.adun.feature.auth.domain.entity.AuthResult
import dot.adun.feature.login.domain.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataRepository @Inject constructor(
    private val api: AuthApi
) : LoginRepository {
    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): AuthResult = api.loginWithEmail(email, password)
}
