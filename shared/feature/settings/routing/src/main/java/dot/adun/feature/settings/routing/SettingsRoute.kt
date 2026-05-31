package dot.adun.feature.settings.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.settings.ui.screen.SettingsScreen
import dot.adun.feature.settings.ui.screen.SettingsViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class SettingsRoute(
    override val id: String = "settings_route"
) : Route<SettingsViewModel> {
    @Composable
    override fun Screen(viewModel: SettingsViewModel) {
        SettingsScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): SettingsViewModel = hiltViewModel<SettingsViewModel>()
}
