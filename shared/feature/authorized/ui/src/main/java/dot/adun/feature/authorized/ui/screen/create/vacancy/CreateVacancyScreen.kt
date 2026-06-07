package dot.adun.feature.authorized.ui.screen.create.vacancy

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.form.VacancyCreateForm

@Composable
fun CreateVacancyScreen(
    viewModel: CreateVacancyViewModel,
) = AppScreen(viewModel) { state, intents ->
    VacancyCreateForm(
        titleField = state.titleField,
        descriptionField = state.descriptionField,
        skills = state.skills,
        skillInput = state.skillInput,
        experienceYears = state.experienceYears,
        paymentMethod = state.paymentMethod,
        budget = state.budget,
        currency = state.currency,
        durationType = state.durationType,
        saveState = state.saveState,
        intents = intents,
    )
}
