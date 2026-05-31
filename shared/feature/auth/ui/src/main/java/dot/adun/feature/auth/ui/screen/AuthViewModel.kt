package dot.adun.feature.auth.ui.screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.auth.domain.AuthModel
import dot.adun.feature.auth.domain.entity.AuthStatus
import dot.adun.core.domain.entity.UserRole
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authModel: AuthModel
) : StateViewModel<State, Intents, Result>(State()) {
    override val intents = Intents()

    init {
        onIntent(intents.login) {
            emitResult(AuthScreenResult.Login)
        }

        onIntent(intents.register) {
            emitResult(AuthScreenResult.Register)
        }

        on(
            combine(
                authModel.authStatusFlow,
                authModel.profile,
                ::Pair
            )
        ) { (status, profile) ->
            update { state ->
                state.copy(authStatus = status)
            }

            action { _ ->
                if (status is AuthStatus.Authenticated) {
                    if (profile == null) {
                        performProfileFetch()
                    } else {
                        if (profile.role == UserRole.None) {
                            emitEvent(
                                chooseRoleDialog(
                                    onConfirm = { role ->
                                        viewModelScope.launch { authModel.updateUserRole(role) }
                                    }
                                )
                            )
                        } else {
                            emitResult(AuthScreenResult.Authorized)
                        }
                    }
                }
            }
        }
    }

    private fun performProfileFetch() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { authModel.fetchProfile() }
            onSuccess { _ -> }
            onError { error -> errorSnack(error) }
        }
    }
}

sealed interface AuthScreenResult {
    data object Finish : AuthScreenResult
    data object Login : AuthScreenResult
    data object Register : AuthScreenResult
    data object Authorized : AuthScreenResult
}

internal typealias State = AuthViewState
internal typealias Intents = AuthViewIntents
internal typealias Result = AuthScreenResult
