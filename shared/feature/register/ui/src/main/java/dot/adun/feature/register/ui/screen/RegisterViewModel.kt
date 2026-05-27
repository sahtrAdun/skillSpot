package dot.adun.feature.register.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.core.event.snackbar.Snackbar
import dot.adun.core.ui.mappers.toUiError
import dot.adun.feature.auth.domain.AuthResult
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
                    passwordField = state.passwordField.validate()
                )
            }
            action { newState ->
                if (newState.isValid) {
                    val result = model.signUpWithEmail(
                        email = newState.emailField.value,
                        password = newState.passwordField.value
                    )

                    when (result) {
                        is AuthResult.Success -> {
                            emitResult(RegisterScreenResult.Success)
                        }
                        is AuthResult.Failure -> {
                            val error = result.error.toUiError()
                            emitEvent(
                                Snackbar(
                                    title = error.title,
                                    message = error.description,
                                    isError = true
                                )
                            )
                        }
                    }
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
