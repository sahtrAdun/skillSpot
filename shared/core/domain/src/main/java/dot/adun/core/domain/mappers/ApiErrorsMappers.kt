package dot.adun.core.domain.mappers

import dot.adun.core.domain.entity.error.ApiError
import dot.adun.core.domain.entity.error.AppError
import dot.adun.core.domain.entity.error.AuthError
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException

fun Exception.toAppError(): AppError {
    return when (this) {
        is HttpRequestTimeoutException,
        is AuthRestException,
        is ResponseException -> this.toApiError()
        else -> AppError.Unknown(this)
    }
}

fun Exception.toApiError(): ApiError {
    return when (this) {
        is HttpRequestTimeoutException -> ApiError.NetworkError
        is AuthRestException -> this.toApiError()
        is ResponseException -> this.toApiError()
        else -> AppError.Unknown(this)
    }
}

private fun ResponseException.toApiError(): ApiError =
    when (response.status.value) {
        401 -> ApiError.Unauthorized
        else -> ApiError.HttpError(
            code = response.status.value,
            serverMessage = localizedMessage
        )
    }

private fun AuthRestException.toApiError(): ApiError {
    return when (errorCode) {
        AuthErrorCode.UserAlreadyExists -> AuthError.EmailExists
        AuthErrorCode.EmailExists -> AuthError.EmailExists
        AuthErrorCode.EmailAddressInvalid -> AuthError.InvalidEmail
        AuthErrorCode.InvalidCredentials -> AuthError.InvalidCredentials
        else -> AppError.Unknown(this)
    }
}
