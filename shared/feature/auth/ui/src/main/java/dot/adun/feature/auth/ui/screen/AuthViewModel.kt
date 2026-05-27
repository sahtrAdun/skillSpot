package dot.adun.feature.auth.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import javax.inject.Inject

@Stable
@HiltViewModel
class AuthViewModel @Inject constructor() : StateViewModel<State, Intents, Result>(State) {
    override val intents = Intents()

    init {
        onIntent(intents.login) {
            emitResult(AuthScreenResult.Login)
        }

        onIntent(intents.register) {
            emitResult(AuthScreenResult.Register)
        }
    }
}

sealed interface AuthScreenResult {
    data object Finish : AuthScreenResult
    data object Login : AuthScreenResult
    data object Register : AuthScreenResult
}

internal typealias State = AuthViewState
internal typealias Intents = AuthViewIntents
internal typealias Result = AuthScreenResult
