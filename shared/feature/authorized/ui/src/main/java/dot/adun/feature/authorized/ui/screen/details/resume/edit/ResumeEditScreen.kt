package dot.adun.feature.authorized.ui.screen.details.resume.edit

import androidx.compose.runtime.Composable
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.details.ResumeEditForm

@Composable
fun ResumeEditScreen(
    viewModel: ResumeEditViewModel,
) = AppScreen(viewModel) { state, intents ->
    ResumeEditForm(
        titleField = state.titleField,
        bioField = state.bioField,
        mainSkills = state.mainSkills,
        mainSkillInput = state.mainSkillInput,
        secondarySkills = state.secondarySkills,
        secondarySkillInput = state.secondarySkillInput,
        paymentPreference = state.paymentPreference,
        minRate = state.minRate,
        currency = state.currency,
        availability = state.availability,
        githubUrl = state.githubUrl,
        portfolioUrl = state.portfolioUrl,
        isActive = state.isActive,
        loadState = state.loadState,
        saveState = state.saveState,
        isValid = state.isValid,
        intents = intents,
    )

    FullScreenLoader(isLoading = state.loadState.isLoading)
}
