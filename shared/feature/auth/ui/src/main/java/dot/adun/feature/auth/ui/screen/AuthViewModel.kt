package dot.adun.feature.auth.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.auth.domain.AuthModel
import dot.adun.feature.auth.domain.entity.AuthStatus
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

        on(authModel.authStatusFlow) { status ->
            update { state ->
                state.copy(authStatus = status)
            }

            action { _ ->
                if (status is AuthStatus.Authenticated) {
                    emitResult(AuthScreenResult.Authorized)
                }
            }
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
