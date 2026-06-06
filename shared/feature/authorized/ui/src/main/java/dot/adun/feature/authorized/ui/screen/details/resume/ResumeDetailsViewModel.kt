package dot.adun.feature.authorized.ui.screen.details.resume

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.profile.domain.ProfileModel

@Stable
@HiltViewModel(assistedFactory = ResumeDetailsViewModel.Factory::class)
class ResumeDetailsViewModel @AssistedInject constructor(
    @Assisted private val resumeId: String,
    private val model: AuthorizedModel,
    private val profileModel: ProfileModel,
) : StateViewModel<ResumeDetailsViewState, ResumeDetailsViewIntents, ResumeDetailsScreenResult>(
    ResumeDetailsViewState()
) {
    @AssistedFactory
    interface Factory {
        fun create(resumeId: String): ResumeDetailsViewModel
    }

    override val intents = ResumeDetailsViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(ResumeDetailsScreenResult.Finish)
        }

        onIntent(intents.edit) {
            emitResult(ResumeDetailsScreenResult.Edit(resumeId))
        }

        onIntent(intents.openProfile) { profileId ->
            emitResult(ResumeDetailsScreenResult.OpenAuthorProfile(profileId))
        }

        on(profileModel.profile) { profile ->
            update { state -> state.copy(currentUserId = profile?.id) }
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
                update { state -> state.copy(resume = resume) }
                loadCreator(resume.freelancerId)
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
