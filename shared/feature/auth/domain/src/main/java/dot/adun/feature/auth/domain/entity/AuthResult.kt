package dot.adun.feature.auth.domain.entity

import dot.adun.core.domain.entity.error.ApiError

sealed interface AuthResult {
    data object Unknown : AuthResult
    data object Success : AuthResult
    data class Failure(val error: ApiError) : AuthResult
}