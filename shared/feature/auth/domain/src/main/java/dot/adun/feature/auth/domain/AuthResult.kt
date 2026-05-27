package dot.adun.feature.auth.domain

import dot.adun.core.domain.entity.ApiError

sealed interface AuthResult {
    data object Success : AuthResult
    data class Failure(val error: ApiError) : AuthResult
}
