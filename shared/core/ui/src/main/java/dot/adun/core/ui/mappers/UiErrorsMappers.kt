package dot.adun.core.ui.mappers

import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.error.ApiError
import dot.adun.core.domain.entity.error.AppError
import dot.adun.core.domain.entity.error.AuthError
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.entity.strRef
import dot.adun.core.ui.entity.UiError

fun AppError.toUiError(): UiError {
    return when (this) {
        is ApiError -> this.toUiError()
        is AppError.Unknown -> UiError(
            title = resRef(Res.strings.error_unknown_title),
            description = this.throwable.localizedMessage?.let { strRef(it) }
                ?: resRef(Res.strings.error_unknown_desc)
        )
    }
}

fun ApiError.toUiError(): UiError {
    return when (this) {
        is AuthError -> toUiError()
        is ApiError.NetworkError -> UiError(
            title = resRef(Res.strings.error_network_title),
            description = resRef(Res.strings.error_network_desc)
        )
        is ApiError.Unauthorized -> UiError(
            title = resRef(Res.strings.error_unauthorized_title),
            description = resRef(Res.strings.error_unauthorized_desc)
        )
        is ApiError.HttpError -> {
            when (this.code) {
                409 -> UiError(
                    title = resRef(Res.strings.error_conflict_title),
                    description = resRef(Res.strings.error_conflict_desc)
                )
                else -> UiError(
                    title = resRef(Res.strings.error_server_title),
                    description = this.serverMessage?.let { strRef(it) }
                        ?: resRef(Res.strings.error_server_desc)
                )
            }
        }
        else -> UiError(
            title = resRef(Res.strings.error_unknown_title),
            description = resRef(Res.strings.error_unknown_desc)
        )
    }
}

private fun AuthError.toUiError(): UiError =
    when (this) {
        AuthError.EmailExists -> UiError(
            title = resRef(Res.strings.auth_error_email_exists),
        )
        AuthError.InvalidEmail -> UiError(
            title = resRef(Res.strings.auth_error_email_invalid),
        )
    }
