package dot.adun.feature.settings.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.settings.ui.component.SettingsLayout

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) = AppScreen(viewModel) { state, intents ->
    SettingsLayout(state = state, intents = intents)
}
