package dot.adun.feature.authorized.ui.screen.details.vacancy.edit

import androidx.compose.runtime.Composable
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.details.VacancyEditForm

@Composable
fun VacancyEditScreen(
    viewModel: VacancyEditViewModel,
) = AppScreen(viewModel) { state, intents ->
    VacancyEditForm(
        titleField = state.titleField,
        descriptionField = state.descriptionField,
        skills = state.skills,
        skillInput = state.skillInput,
        experienceYears = state.experienceYears,
        paymentMethod = state.paymentMethod,
        budget = state.budget,
        currency = state.currency,
        loadState = state.loadState,
        saveState = state.saveState,
        isValid = state.isValid,
        intents = intents,
    )

    FullScreenLoader(isLoading = state.loadState.isLoading)
}
