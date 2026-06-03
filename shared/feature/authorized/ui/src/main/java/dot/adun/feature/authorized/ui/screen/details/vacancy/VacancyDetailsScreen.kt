package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.details.VacancyDetailsContent

@Composable
fun VacancyDetailsScreen(
    viewModel: VacancyDetailsViewModel,
) = AppScreen(viewModel) { state, intents ->
    VacancyDetailsContent(
        vacancy = state.vacancy,
        creator = state.creator,
        loadState = state.loadState,
        creatorLoadState = state.creatorLoadState,
        canEdit = state.canEdit,
        intents = intents,
    )
}
