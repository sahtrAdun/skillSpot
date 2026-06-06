package dot.adun.feature.profile.ui.screen

import androidx.compose.runtime.Composable
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.profile.ui.component.ProfileLayout

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
) = AppScreen(viewModel) { state, intents ->
    ProfileLayout(
        profile = state.profile,
        reviews = state.reviews,
        content = state.content,
        selectedTab = state.selectedTab,
        reviewsLoadState = state.reviewsLoadState,
        contentLoadState = state.contentLoadState,
        intents = intents,
    )

    FullScreenLoader(isLoading = state.loadState.isLoading && state.profile == null)
}
