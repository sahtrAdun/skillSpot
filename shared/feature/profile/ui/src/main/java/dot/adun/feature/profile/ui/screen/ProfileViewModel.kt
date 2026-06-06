package dot.adun.feature.profile.ui.screen

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.profile.domain.ProfileModel

@Stable
@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted private val profileId: String,
    private val model: ProfileModel,
) : StateViewModel<ProfileViewState, ProfileViewIntents, ProfileScreenResult>(
    ProfileViewState()
) {
    @AssistedFactory
    interface Factory {
        fun create(profileId: String): ProfileViewModel
    }

    override val intents = ProfileViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(ProfileScreenResult.Finish)
        }

        onIntent(intents.selectTab) { tab ->
            update { state -> state.copy(selectedTab = tab) }
        }

        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(ProfileScreenResult.Details(isVacancy = isVacancy, id = id))
        }

        onIntent(intents.openProfile) { otherProfileId ->
            emitResult(ProfileScreenResult.OtherProfile(otherProfileId))
        }

        loadProfile()
    }

    private fun loadProfile() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getProfileById(profileId) }
            onSuccess { profile ->
                update { state -> state.copy(profile = profile) }
                loadReviews()
                loadContent()
            }
            onError { errorSnack(it) }
        }
    }

    private fun loadReviews() {
        task(
            stateRead = { vs -> vs.reviewsLoadState },
            stateWrite = { vs, ls -> vs.copy(reviewsLoadState = ls) }
        ) {
            job { model.getReviews(profileId) }
            onSuccess { reviews ->
                update { state -> state.copy(reviews = reviews) }
            }
        }
    }

    private fun loadContent() {
        task(
            stateRead = { vs -> vs.contentLoadState },
            stateWrite = { vs, ls -> vs.copy(contentLoadState = ls) }
        ) {
            job { model.getContent(profileId) }
            onSuccess { content ->
                update { state -> state.copy(content = content) }
            }
        }
    }
}
