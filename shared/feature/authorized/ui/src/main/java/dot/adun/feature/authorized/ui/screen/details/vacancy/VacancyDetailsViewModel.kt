package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.profile.domain.ProfileModel

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

        on(profileModel.profile) { profile ->
            update { state -> state.copy(currentUserId = profile?.id) }
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
}
