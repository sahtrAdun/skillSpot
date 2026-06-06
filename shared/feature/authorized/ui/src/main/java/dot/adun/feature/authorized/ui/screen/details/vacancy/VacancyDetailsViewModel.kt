package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.profile.domain.ProfileModel
import dot.adun.feature.profile.domain.entity.ProfileContent

@Stable
@HiltViewModel(assistedFactory = VacancyDetailsViewModel.Factory::class)
class VacancyDetailsViewModel @AssistedInject constructor(
    @Assisted val vacancyId: String,
    private val model: AuthorizedModel,
    private val profileModel: ProfileModel,
) : StateViewModel<VacancyDetailsViewState, VacancyDetailsViewIntents, VacancyDetailsScreenResult>(
    VacancyDetailsViewState()
) {
    @AssistedFactory
    interface Factory {
        fun create(vacancyId: String): VacancyDetailsViewModel
    }

    override val intents = VacancyDetailsViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(VacancyDetailsScreenResult.Finish)
        }

        onIntent(intents.edit) {
            emitResult(VacancyDetailsScreenResult.Edit(vacancyId))
        }

        onIntent(intents.openProfile) { profileId ->
            emitResult(VacancyDetailsScreenResult.OpenAuthorProfile(profileId))
        }

        onIntent(intents.openApplySheet) {
            update { state -> state.copy(showApplySheet = true) }
            loadResumes()
        }

        onIntent(intents.dismissApplySheet) {
            update { state -> state.copy(showApplySheet = false) }
        }

        onIntent(intents.apply) { args ->
            apply(args)
        }

        on(profileModel.profile) { profile ->
            update { state ->
                state.copy(
                    currentUserId = profile?.id,
                    currentUserRole = profile?.role,
                )
            }
        }

        loadVacancy()
    }

    private fun loadVacancy(skipCache: Boolean = false) {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getVacancyById(vacancyId) }
            onSuccess { vacancy ->
                update { state -> state.copy(vacancy = vacancy) }
                loadCreator(vacancy.clientId)
            }
            onError { errorSnack(it) }
        }
    }

    private fun loadCreator(profileId: String) {
        task(
            stateRead = { vs -> vs.creatorLoadState },
            stateWrite = { vs, ls -> vs.copy(creatorLoadState = ls) }
        ) {
            job { model.getProfileById(profileId) }
            onSuccess { creator ->
                update { state -> state.copy(creator = creator) }
            }
        }
    }

    private fun loadResumes() {
        task(
            stateRead = { vs -> vs.resumesLoadState },
            stateWrite = { vs, ls -> vs.copy(resumesLoadState = ls) }
        ) {
            job { state ->
                val profileId = state.currentUserId ?: return@job emptyList()
                when (val content = profileModel.getContent(profileId)) {
                    is ProfileContent.Resumes -> content.items
                    else -> emptyList()
                }
            }
            onSuccess { resumes ->
                update { state -> state.copy(resumes = resumes) }
            }
            onError { errorSnack(it) }
        }
    }

    private fun apply(args: ApplyArgs) {
        task(
            stateRead = { vs -> vs.applyState },
            stateWrite = { vs, ls -> vs.copy(applyState = ls) }
        ) {
            job {
                model.applyForVacancy(
                    vacancyId = vacancyId,
                    resumeId = args.resumeId,
                    coverLetter = args.coverLetter,
                )
            }
            onSuccess {
                update { state -> state.copy(applied = true, showApplySheet = false) }
                simpleSnackbar(resRef(R.string.apply_success))
            }
            onError { errorSnack(it) }
        }
    }
}
