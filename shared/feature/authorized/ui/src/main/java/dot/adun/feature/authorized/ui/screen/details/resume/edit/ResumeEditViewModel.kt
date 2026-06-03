package dot.adun.feature.authorized.ui.screen.details.resume.edit

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsViewModel
import javax.inject.Inject

@Stable
@HiltViewModel(assistedFactory = ResumeEditViewModel.Factory::class)
class ResumeEditViewModel @AssistedInject constructor(
    @Assisted private val resumeId: String,
    private val model: AuthorizedModel,
) : StateViewModel<ResumeEditViewState, ResumeEditViewIntents, ResumeEditScreenResult>(
    ResumeEditViewState()
) {
    @AssistedFactory
    interface Factory {
        fun create(resumeId: String): ResumeEditViewModel
    }

    override val intents = ResumeEditViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(ResumeEditScreenResult.Finish)
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

        onIntent(intents.toggleActive) {
            update { state -> state.copy(isActive = !state.isActive) }
        }

        onIntent(intents.save) {
            action { state ->
                if (!state.isValid) return@action
                update { it.copy(saveState = LoadState.Loading) }
                performSave()
            }
        }

        loadResume()
    }

    private fun loadResume() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getResumeById(resumeId) }
            onSuccess { resume ->
                update { state ->
                    state.copy(
                        resume = resume,
                        titleField = state.titleField.update(resume.title),
                        bioField = resume.bio?.let { state.bioField.update(it) }
                            ?: state.bioField,
                        mainSkills = resume.skills.main,
                        secondarySkills = resume.skills.secondary ?: emptyList(),
                        paymentPreference = resume.paymentInfo.preference,
                        minRate = resume.paymentInfo.minRate
                            .toBigDecimal().stripTrailingZeros().toPlainString(),
                        currency = resume.paymentInfo.currency,
                        availability = resume.availability,
                        githubUrl = resume.links.githubUrl ?: "",
                        portfolioUrl = resume.links.portfolioUrl ?: "",
                        isActive = resume.isActive,
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
                val original = requireNotNull(state.resume)
                model.updateResume(
                    original.copy(
                        title = state.titleField.value,
                        bio = state.bioField.value.ifBlank { null },
                        links = Resume.Links(
                            githubUrl = state.githubUrl.ifBlank { null },
                            portfolioUrl = state.portfolioUrl.ifBlank { null },
                        ),
                        skills = Resume.Skills(
                            main = state.mainSkills,
                            secondary = state.secondarySkills.ifEmpty { null },
                        ),
                        paymentInfo = Resume.PaymentInfo(
                            preference = state.paymentPreference,
                            minRate = state.minRate.toDoubleOrNull()
                                ?: original.paymentInfo.minRate,
                            currency = state.currency,
                        ),
                        availability = state.availability,
                        isActive = state.isActive,
                    )
                )
            }
            onSuccess {
                emitResult(ResumeEditScreenResult.Saved)
            }
            onError { errorSnack(it) }
        }
    }
}
