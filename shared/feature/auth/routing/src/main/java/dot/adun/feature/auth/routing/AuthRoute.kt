package dot.adun.feature.auth.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.auth.ui.screen.AuthScreen
import dot.adun.feature.auth.ui.screen.AuthViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AuthRoute(
    override val id: String = "auth_route"
) : Route<AuthViewModel> {
    @Composable
    override fun Screen(viewModel: AuthViewModel) {
        AuthScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): AuthViewModel = hiltViewModel<AuthViewModel>()
}
