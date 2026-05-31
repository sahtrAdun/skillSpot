package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.ActiveLayout

@Composable
fun ActiveScreen(
    viewModel: ActiveViewModel,
) = AppScreen(viewModel) { state, intents ->
    ActiveLayout(
        vacancies = state.vacancies,
        activeProjects = state.activeProjects,
        loadState = state.loadState,
        intents = intents
    )
}
