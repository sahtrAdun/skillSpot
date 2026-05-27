package dot.adun.feature.login.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.auth.domain.entity.AuthResult
import dot.adun.feature.login.domain.LoginModel
import javax.inject.Inject

@Stable
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val model: LoginModel
) : StateViewModel<State, Intents, ScreenResult>(State()) {
    override val intents = Intents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(LoginScreenResult.Finish)
        }

        onIntent(intents.register) {
            emitResult(LoginScreenResult.Register)
        }

        onIntent(intents.login) {
            update { state ->
                state.copy(
                    emailField = state.emailField.validate(),
                    passwordField = state.passwordField.validate(),
                )
            }
            action { newState ->
                if (newState.isValid) { performLogin() }
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

    private fun performLogin() {
        runJob<AuthResult>(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { state ->
                if (!state.isValid) return@job AuthResult.Unknown

                model.loginWithEmail(
                    email = state.emailField.value,
                    password = state.passwordField.value
                )
            }

            onSuccess { result ->
                when (result) {
                    AuthResult.Success -> emitResult(LoginScreenResult.Success)
                    else -> Unit
                }
            }

            onError { error -> errorSnack(error) }
        }
    }
}

@Immutable
sealed interface LoginScreenResult {
    data object Finish : LoginScreenResult
    data object Register : LoginScreenResult
    data object Success : LoginScreenResult
}

internal typealias State = LoginViewState
internal typealias Intents = LoginViewIntents
internal typealias ScreenResult = LoginScreenResult
