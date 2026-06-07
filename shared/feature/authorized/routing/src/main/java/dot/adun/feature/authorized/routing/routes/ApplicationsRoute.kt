package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.applications.ApplicationsScreen
import dot.adun.feature.authorized.ui.screen.applications.ApplicationsViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ApplicationsRoute(
    override val id: String = "applications_route"
) : Route<ApplicationsViewModel> {
    @Composable
    override fun Screen(viewModel: ApplicationsViewModel) {
        ApplicationsScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ApplicationsViewModel = hiltViewModel()
}
