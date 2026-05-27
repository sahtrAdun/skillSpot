package dot.adun.feature.register.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.register.ui.screen.RegisterScreen
import dot.adun.feature.register.ui.screen.RegisterViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class RegisterRoute(
    override val id: String = "register_route"
) : Route<RegisterViewModel> {
    @Composable
    override fun Screen(viewModel: RegisterViewModel) {
        RegisterScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): RegisterViewModel = hiltViewModel<RegisterViewModel>()
}
