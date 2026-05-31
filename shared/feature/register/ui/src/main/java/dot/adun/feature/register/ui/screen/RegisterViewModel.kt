package dot.adun.feature.register.ui.screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.util.notNull
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.auth.domain.AuthModel
import dot.adun.feature.auth.domain.entity.AuthResult
import dot.adun.feature.auth.ui.screen.chooseRoleDialog
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.register.domain.RegistrationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val model: RegistrationModel,
    private val authModel: AuthModel
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
                    errorSnack(resRef(Res.strings.password_error_match))
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

        on(authModel.profile.notNull()) { profile ->
            action { _ ->
                if (profile.role == UserRole.None) {
                    emitEvent(
                        chooseRoleDialog(
                            onConfirm = { role ->
                                viewModelScope.launch { authModel.updateUserRole(role) }
                            }
                        )
                    )
                } else {
                    emitResult(RegisterScreenResult.Success)
                }
            }
        }
    }

    private fun performRegistration() {
        task(
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
                    AuthResult.Success -> performPostRegister()
                    else -> Unit
                }
            }

            onError { error -> errorSnack(error) }
        }
    }

    private fun performPostRegister() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { _ ->
                authModel.rememberAuth()
                authModel.fetchProfile()
            }

            onSuccess { _ -> }
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
