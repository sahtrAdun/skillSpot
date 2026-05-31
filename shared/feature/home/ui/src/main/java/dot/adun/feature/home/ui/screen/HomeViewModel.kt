package dot.adun.feature.home.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.home.domain.HomeModel
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject

@Stable
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeModel: HomeModel,
    private val profileModel: ProfileModel
) : StateViewModel<HomeViewState, HomeViewIntents, HomeScreenResult>(HomeViewState()) {
    override val intents = HomeViewIntents()

    init {
        performProfileFetch()

        onIntent(intents.logout) {
            action { performLogout() }
        }

        onIntent(intents.navToSearch) {
            emitResult(HomeScreenResult.Search)
        }
    }

    private fun performProfileFetch() {
        task {
            job { profileModel.fetchProfile() }
            onError { _ -> errorSnack(resRef(Res.strings.error_profile_fetch)) }
        }
    }

    private fun performLogout() {
        task {
            job { profileModel.logout() }
            onSuccess { emitResult(HomeScreenResult.Logout) }
            onError { error -> errorSnack(error) }
        }
    }
}

sealed interface HomeScreenResult {
    data object Search : HomeScreenResult
    data object Finish : HomeScreenResult
    data object Logout : HomeScreenResult
}
