package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.active.ActiveScreen
import dot.adun.feature.authorized.ui.screen.active.ActiveViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ActiveRoute(
    override val id: String = "home_route"
) : Route<ActiveViewModel> {
    @Composable
    override fun Screen(viewModel: ActiveViewModel) {
        ActiveScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ActiveViewModel = hiltViewModel<ActiveViewModel>()
}