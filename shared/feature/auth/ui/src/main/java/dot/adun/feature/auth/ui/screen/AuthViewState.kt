package dot.adun.feature.auth.ui.screen

import androidx.compose.runtime.Immutable
import dot.adun.feature.auth.domain.entity.AuthStatus

@Immutable
data class AuthViewState(
    val authStatus: AuthStatus = AuthStatus.Loading
)
