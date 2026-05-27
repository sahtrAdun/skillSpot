package dot.adun.feature.login.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.login.ui.screen.LoginScreen
import dot.adun.feature.login.ui.screen.LoginViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class LoginRoute(
    override val id: String = "register_route"
) : Route<LoginViewModel> {
    @Composable
    override fun Screen(viewModel: LoginViewModel) {
        LoginScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): LoginViewModel = hiltViewModel<LoginViewModel>()
}

