package dot.adun.feature.home.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.buildPagingParams
import dot.adun.feature.home.domain.HomeModel
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject

@Stable
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeModel: HomeModel,
    private val profileModel: ProfileModel,
    private val authorizedModel: AuthorizedModel
) : StateViewModel<HomeViewState, HomeViewIntents, HomeScreenResult>(HomeViewState()) {
    private var initialFetch by mutableStateOf(true)
    override val intents = HomeViewIntents()

    init {
        performProfileFetch()

        onIntent(intents.logout) {
            action { performLogout() }
        }

        onIntent(intents.navToSearch) {
            emitResult(HomeScreenResult.Search)
        }

        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(HomeScreenResult.Details(isVacancy, id))
        }

        onIntent(intents.openProfile) {
            profileModel.readProfile()?.let { profile ->
                emitResult(HomeScreenResult.Profile(profile.id))
            }
        }

        onIntent(intents.openApplications) {
            emitResult(HomeScreenResult.Applications)
        }

        onIntent(intents.refresh) {
            action { _ -> resolveUserRoleActions(true) }
        }

        on(profileModel.profile) { profile ->
            update { state -> state.copy(userRole = profile?.role ?: UserRole.None) }
            action { state ->
                if (state.emptyForUser()) { resolveUserRoleActions(initialFetch) }
            }
        }
    }

    private fun resolveUserRoleActions(skipCache: Boolean) {
        when (state.value.userRole) {
            UserRole.Freelancer -> performVacancies(skipCache)
            UserRole.Customer -> performResumes(skipCache)
            UserRole.None -> Unit
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

    private fun performResumes(skipCache: Boolean = false) {
        task(
            stateRead = { vs ->vs.loadState },
            stateWrite = { vs, ls -> vs.refreshState(skipCache) { vs.copy(loadState = ls) } }
        ) {
            job { state ->
                val params = buildPagingParams(
                    listSize = state.recommendedResumes.size,
                    skipCache = skipCache
                )

                authorizedModel.getAllRecommendedResumes(params)
            }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        recommendedResumes = (state.recommendedResumes + paging.data)
                            .takeIf { !skipCache }
                            ?: paging.data
                    )
                }
            }
            onError { errorSnack(it) }
            onAny { initialFetch = false }
        }
    }

    private fun performVacancies(skipCache: Boolean = false) {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.refreshState(skipCache) { vs.copy(loadState = ls) } }
        ) {
            job { state ->
                val params = buildPagingParams(
                    listSize = state.recommendedVacancies.size,
                    skipCache = skipCache
                )

                authorizedModel.getAllRecommendedVacancies(params)
            }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        recommendedVacancies = (state.recommendedVacancies + paging.data)
                            .takeIf { !skipCache }
                            ?: paging.data
                    )
                }
            }
            onError { errorSnack(it) }
            onAny { initialFetch = false }
        }
    }

    private fun HomeViewState.refreshState(
        skipCache: Boolean,
        select: (HomeViewState) -> HomeViewState
    ) = select(this)
        .copy(refreshing = loadState.isLoading && skipCache)
}

sealed interface HomeScreenResult {
    data object Search : HomeScreenResult
    data object Finish : HomeScreenResult
    data object Logout : HomeScreenResult
    data object Applications : HomeScreenResult

    @Immutable
    data class Details(val isVacancy: Boolean, val id: String) : HomeScreenResult

    @Immutable
    data class Profile(val id: String) : HomeScreenResult
}
