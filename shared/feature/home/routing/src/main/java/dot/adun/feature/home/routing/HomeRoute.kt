package dot.adun.feature.home.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.home.ui.HomeScreen
import dot.adun.feature.home.ui.HomeViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class HomeRoute(
    override val id: String = "home_route"
) : Route<HomeViewModel> {
    @Composable
    override fun Screen(viewModel: HomeViewModel) {
        HomeScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel<HomeViewModel>()
}
