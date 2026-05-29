package dot.adun.feature.login.domain

import javax.inject.Inject

class LoginModel @Inject constructor(
    private val repository: LoginRepository
) {
    suspend fun loginWithEmail(email: String, password: String) =
        repository.loginWithEmail(email, password)
}
