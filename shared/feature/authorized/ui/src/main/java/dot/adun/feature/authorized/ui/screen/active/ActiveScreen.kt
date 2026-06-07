package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.ActiveLayout
import dot.adun.feature.authorized.ui.component.LeaveReviewDialog

@Composable
fun ActiveScreen(
    viewModel: ActiveViewModel,
) = AppScreen(viewModel) { state, intents ->
    ActiveLayout(
        vacancies = state.vacancies,
        activeProjects = state.activeProjects,
        loadState = state.loadState,
        completingProjectId = state.completingProjectId,
        intents = intents
    )

    state.reviewProjectId?.let { projectId ->
        LeaveReviewDialog(
            submitState = state.reviewState,
            onSubmit = { rating, comment ->
                intents.submitReview(ReviewArgs(projectId, rating, comment))
            },
            onDismiss = intents.dismissReview,
        )
    }
}
