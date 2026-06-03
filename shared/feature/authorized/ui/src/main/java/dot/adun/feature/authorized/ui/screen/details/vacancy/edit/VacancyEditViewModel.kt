package dot.adun.feature.authorized.ui.screen.details.vacancy.edit

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.screen.details.vacancy.VacancyDetailsViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Stable
@HiltViewModel(assistedFactory = VacancyEditViewModel.Factory::class)
class VacancyEditViewModel @AssistedInject constructor(
    @Assisted private val vacancyId: String,
    private val model: AuthorizedModel,
) : StateViewModel<VacancyEditViewState, VacancyEditViewIntents, VacancyEditScreenResult>(
    VacancyEditViewState()
) {
    @AssistedFactory
    interface Factory {
        fun create(vacancyId: String): VacancyEditViewModel
    }

    override val intents = VacancyEditViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(VacancyEditScreenResult.Finish)
        }

        onIntent(intents.changeTitle) { title ->
            update { state -> state.copy(titleField = state.titleField.update(title)) }
        }

        onIntent(intents.changeDescription) { description ->
            update { state ->
                state.copy(descriptionField = state.descriptionField.update(description))
            }
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

        onIntent(intents.save) {
            action { state ->
                if (!state.isValid) return@action
                update { it.copy(saveState = LoadState.Loading) }
                performSave()
            }
        }

        loadVacancy()
    }

    private fun loadVacancy() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { delay(2000); model.getVacancyById(vacancyId) }
            onSuccess { vacancy ->
                update { state ->
                    state.copy(
                        vacancy = vacancy,
                        titleField = state.titleField.update(vacancy.info.title),
                        descriptionField = state.descriptionField.update(vacancy.info.description),
                        skills = vacancy.requirements.requiredSkills,
                        experienceYears = vacancy.requirements.experienceYearsRequired?.toString() ?: "",
                        paymentMethod = vacancy.payment.method,
                        budget = vacancy.payment.budget.toBigDecimal()
                            .stripTrailingZeros().toPlainString(),
                        currency = vacancy.payment.currency,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun performSave() {
        task(
            stateRead = { vs -> vs.saveState },
            stateWrite = { vs, ls -> vs.copy(saveState = ls) }
        ) {
            job { state ->
                val original = requireNotNull(state.vacancy)
                model.updateVacancy(
                    original.copy(
                        info = Vacancy.Info(
                            title = state.titleField.value,
                            description = state.descriptionField.value,
                        ),
                        requirements = Vacancy.Requirements(
                            requiredSkills = state.skills,
                            experienceYearsRequired = state.experienceYears.toIntOrNull(),
                        ),
                        payment = Vacancy.Payment(
                            method = state.paymentMethod,
                            budget = state.toBudgetDouble() ?: original.payment.budget,
                            currency = state.currency,
                        ),
                    )
                )
            }
            onSuccess {
                emitResult(VacancyEditScreenResult.Saved)
            }
            onError { errorSnack(it) }
        }
    }
}
