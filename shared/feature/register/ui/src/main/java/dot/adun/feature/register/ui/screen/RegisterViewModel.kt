package dot.adun.feature.register.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.core.event.snackbar.Snackbar
import dot.adun.feature.auth.domain.entity.AuthResult
import dot.adun.feature.register.domain.RegistrationModel
import javax.inject.Inject

@Stable
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val model: RegistrationModel
) : StateViewModel<State, Intents, Result>(State()) {
    override val intents = Intents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(RegisterScreenResult.Finish)
        }

        onIntent(intents.login) {
            emitResult(RegisterScreenResult.Login)
        }

        onIntent(intents.register) {
            update { state ->
                state.copy(
                    emailField = state.emailField.validate(),
                    passwordField = state.passwordField.validate(),
                    secondPasswordField = state.secondPasswordField.validate()
                )
            }
            action { newState ->
                if (newState.secondPasswordField.value != newState.passwordField.value) {
                    emitEvent(
                        Snackbar(
                            title = resRef(Res.strings.password_error_match),
                            message = null,
                            isError = true
                        )
                    )
                    return@action
                }

                if (newState.isValid) {
                    performRegistration()
                }
            }
        }

        onIntent(intents.changeEmail) { email ->
            update { state ->
                state.copy(
                    emailField = state.emailField.update(email)
                )
            }
        }

        onIntent(intents.changePassword) { password ->
            update { state ->
                state.copy(
                    passwordField = state.passwordField.update(password)
                )
            }
        }

        onIntent(intents.changeSecondPassword) { password ->
            update { state ->
                state.copy(
                    secondPasswordField = state.secondPasswordField.update(password)
                )
            }
        }
    }

    private fun performRegistration() {
        runJob<AuthResult>(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { state ->
                if (!state.isValid) return@job AuthResult.Unknown

                model.signUpWithEmail(
                    email = state.emailField.value,
                    password = state.passwordField.value
                )
            }

            onSuccess { result ->
                when (result) {
                    AuthResult.Success -> emitResult(RegisterScreenResult.Success)
                    else -> Unit
                }
            }

            onError { error -> errorSnack(error) }
        }
    }
}

sealed interface RegisterScreenResult {
    data object Finish : RegisterScreenResult
    data object Login : RegisterScreenResult
    data object Success : RegisterScreenResult
}

internal typealias State = RegisterViewState
internal typealias Intents = RegisterViewIntents
internal typealias Result = RegisterScreenResult
