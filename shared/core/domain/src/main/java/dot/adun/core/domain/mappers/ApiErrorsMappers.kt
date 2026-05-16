package dot.adun.core.domain.mappers

import dot.adun.core.domain.entity.ApiError
import dot.adun.core.domain.entity.AuthError
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.ktor.client.plugins.HttpRequestTimeoutException

fun Exception.toApiError(): ApiError {
    return when (this) {
        is HttpRequestTimeoutException -> ApiError.NetworkError
        is AuthRestException -> this.toApiError()
        is io.ktor.client.plugins.ResponseException -> this.toApiError()
        else -> ApiError.Unknown(this)
    }
}

private fun io.ktor.client.plugins.ResponseException.toApiError(): ApiError =
    when (response.status.value) {
        401 -> ApiError.Unauthorized
        else -> ApiError.HttpError(
            code = response.status.value,
            serverMessage = localizedMessage
        )
    }

private fun AuthRestException.toApiError(): ApiError {
    return when (errorCode) {
        AuthErrorCode.EmailExists -> AuthError.EmailExists
        AuthErrorCode.EmailAddressInvalid -> AuthError.InvalidEmail
        AuthErrorCode.InvalidCredentials -> ApiError.Unauthorized
        else -> ApiError.Unknown(this)
    }
}
