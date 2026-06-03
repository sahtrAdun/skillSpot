package dot.adun.feature.authorized.ui.screen.details.resume

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.details.ResumeDetailsContent

@Composable
fun ResumeDetailsScreen(
    viewModel: ResumeDetailsViewModel,
) = AppScreen(viewModel) { state, intents ->
    ResumeDetailsContent(
        resume = state.resume,
        creator = state.creator,
        loadState = state.loadState,
        creatorLoadState = state.creatorLoadState,
        canEdit = state.canEdit,
        intents = intents,
    )
}
