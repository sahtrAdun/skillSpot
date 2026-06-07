package dot.adun.feature.authorized.ui.screen.create.vacancy

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.NewVacancy
import javax.inject.Inject

@Stable
@HiltViewModel
class CreateVacancyViewModel @Inject constructor(
    private val model: AuthorizedModel,
) : StateViewModel<CreateVacancyViewState, CreateVacancyViewIntents, CreateVacancyScreenResult>(
    CreateVacancyViewState()
) {
    override val intents = CreateVacancyViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(CreateVacancyScreenResult.Finish)
        }

        onIntent(intents.changeTitle) { title ->
            update { state -> state.copy(titleField = state.titleField.update(title)) }
        }

        onIntent(intents.changeDescription) { description ->
            update { state -> state.copy(descriptionField = state.descriptionField.update(description)) }
        }

        onIntent(intents.changeSkillInput) { input ->
            update { state -> state.copy(skillInput = input) }
        }

        onIntent(intents.addSkill) {
            action { state ->
                val skill = state.skillInput.trim()
                if (skill.isNotBlank() && skill !in state.skills) {
                    update { it.copy(skills = it.skills + skill, skillInput = "") }
                }
            }
        }

        onIntent(intents.removeSkill) { skill ->
            update { state -> state.copy(skills = state.skills - skill) }
        }

        onIntent(intents.changeExperience) { exp ->
            update { state -> state.copy(experienceYears = exp) }
        }

        onIntent(intents.changePaymentMethod) { method ->
            update { state -> state.copy(paymentMethod = method) }
        }

        onIntent(intents.changeBudget) { budget ->
            update { state -> state.copy(budget = budget) }
        }

        onIntent(intents.changeCurrency) { currency ->
            update { state -> state.copy(currency = currency) }
        }

        onIntent(intents.changeDurationType) { type ->
            update { state -> state.copy(durationType = type) }
        }

        onIntent(intents.save) {
            action { state ->
                if (!state.isValid) return@action
                performSave()
            }
        }
    }

    private fun performSave() {
        task(
            stateRead = { vs -> vs.saveState },
            stateWrite = { vs, ls -> vs.copy(saveState = ls) }
        ) {
            job { state ->
                model.createVacancy(
                    NewVacancy(
                        title = state.titleField.value.trim(),
                        description = state.descriptionField.value.trim(),
                        requiredSkills = state.skills,
                        experienceYears = state.experienceYears.toIntOrNull(),
                        paymentMethod = state.paymentMethod,
                        budget = state.budget.toDoubleOrNull() ?: 0.0,
                        currency = state.currency,
                        durationType = state.durationType,
                    )
                )
            }
            onSuccess {
                emitResult(CreateVacancyScreenResult.Created)
            }
            onError { errorSnack(it) }
        }
    }
}
