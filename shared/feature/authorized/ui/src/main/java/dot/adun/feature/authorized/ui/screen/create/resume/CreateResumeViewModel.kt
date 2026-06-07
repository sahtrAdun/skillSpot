package dot.adun.feature.authorized.ui.screen.create.resume

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.NewResume
import javax.inject.Inject

@Stable
@HiltViewModel
class CreateResumeViewModel @Inject constructor(
    private val model: AuthorizedModel,
) : StateViewModel<CreateResumeViewState, CreateResumeViewIntents, CreateResumeScreenResult>(
    CreateResumeViewState()
) {
    override val intents = CreateResumeViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(CreateResumeScreenResult.Finish)
        }

        onIntent(intents.changeTitle) { title ->
            update { state -> state.copy(titleField = state.titleField.update(title)) }
        }

        onIntent(intents.changeBio) { bio ->
            update { state -> state.copy(bioField = state.bioField.update(bio)) }
        }

        onIntent(intents.changeMainSkillInput) { input ->
            update { state -> state.copy(mainSkillInput = input) }
        }

        onIntent(intents.addMainSkill) {
            action { state ->
                val skill = state.mainSkillInput.trim()
                if (skill.isNotBlank() && skill !in state.mainSkills) {
                    update { it.copy(mainSkills = it.mainSkills + skill, mainSkillInput = "") }
                }
            }
        }

        onIntent(intents.removeMainSkill) { skill ->
            update { state -> state.copy(mainSkills = state.mainSkills - skill) }
        }

        onIntent(intents.changeSecondarySkillInput) { input ->
            update { state -> state.copy(secondarySkillInput = input) }
        }

        onIntent(intents.addSecondarySkill) {
            action { state ->
                val skill = state.secondarySkillInput.trim()
                if (skill.isNotBlank() && skill !in state.secondarySkills) {
                    update {
                        it.copy(
                            secondarySkills = it.secondarySkills + skill,
                            secondarySkillInput = ""
                        )
                    }
                }
            }
        }

        onIntent(intents.removeSecondarySkill) { skill ->
            update { state -> state.copy(secondarySkills = state.secondarySkills - skill) }
        }

        onIntent(intents.changePaymentPreference) { pref ->
            update { state -> state.copy(paymentPreference = pref) }
        }

        onIntent(intents.changeMinRate) { rate ->
            update { state -> state.copy(minRate = rate) }
        }

        onIntent(intents.changeCurrency) { currency ->
            update { state -> state.copy(currency = currency) }
        }

        onIntent(intents.toggleAvailability) { type ->
            action { state ->
                val updated = if (type in state.availability) {
                    state.availability - type
                } else {
                    state.availability + type
                }
                update { it.copy(availability = updated) }
            }
        }

        onIntent(intents.changeGithubUrl) { url ->
            update { state -> state.copy(githubUrl = url) }
        }

        onIntent(intents.changePortfolioUrl) { url ->
            update { state -> state.copy(portfolioUrl = url) }
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
                model.createResume(
                    NewResume(
                        title = state.titleField.value.trim(),
                        bio = state.bioField.value.trim().ifBlank { null },
                        githubUrl = state.githubUrl.trim().ifBlank { null },
                        portfolioUrl = state.portfolioUrl.trim().ifBlank { null },
                        mainSkills = state.mainSkills,
                        secondarySkills = state.secondarySkills.ifEmpty { null },
                        paymentPreference = state.paymentPreference,
                        minRate = state.minRate.toDoubleOrNull() ?: 0.0,
                        currency = state.currency,
                        availability = state.availability,
                    )
                )
            }
            onSuccess {
                emitResult(CreateResumeScreenResult.Created)
            }
            onError { errorSnack(it) }
        }
    }
}
