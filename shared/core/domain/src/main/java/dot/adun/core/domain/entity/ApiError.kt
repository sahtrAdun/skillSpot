package dot.adun.core.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface ApiError {
    data class HttpError(val code: Int, val serverMessage: String?) : ApiError
    object NetworkError : ApiError
    object Unauthorized : ApiError
    data class Unknown(val throwable: Throwable) : ApiError
}

@Immutable
sealed interface AuthError : ApiError {
    data object EmailExists : AuthError
    data object InvalidEmail : AuthError
}
