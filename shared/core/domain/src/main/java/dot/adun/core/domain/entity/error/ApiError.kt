package dot.adun.core.domain.entity.error

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface ApiError : AppError {
    data class HttpError(val code: Int, val serverMessage: String?) : ApiError
    object NetworkError : ApiError
    object Unauthorized : ApiError
}

@Immutable
sealed interface AuthError : ApiError {
    data object EmailExists : AuthError
    data object InvalidEmail : AuthError
    data object InvalidCredentials : AuthError
}
