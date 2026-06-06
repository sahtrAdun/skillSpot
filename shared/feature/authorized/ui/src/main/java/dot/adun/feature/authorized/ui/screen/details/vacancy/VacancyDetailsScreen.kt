package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.details.ApplyVacancySheet
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
        canApply = state.canApply,
        applied = state.applied,
        intents = intents,
    )

    if (state.showApplySheet) {
        ApplyVacancySheet(
            resumes = state.resumes,
            resumesLoadState = state.resumesLoadState,
            applyState = state.applyState,
            onApply = { resumeId, coverLetter ->
                intents.apply(ApplyArgs(resumeId = resumeId, coverLetter = coverLetter))
            },
            onDismiss = intents.dismissApplySheet,
        )
    }
}
