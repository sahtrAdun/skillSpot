package dot.adun.feature.authorized.ui.screen.create.resume

import androidx.compose.runtime.Composable
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.authorized.ui.component.form.ResumeCreateForm

@Composable
fun CreateResumeScreen(
    viewModel: CreateResumeViewModel,
) = AppScreen(viewModel) { state, intents ->
    ResumeCreateForm(
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
        saveState = state.saveState,
        intents = intents,
    )
}
