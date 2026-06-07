package dot.adun.feature.authorized.ui.screen.myitems

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.buildPagingParams
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject

@Stable
@HiltViewModel
class MyItemsViewModel @Inject constructor(
    private val model: AuthorizedModel,
    private val profileModel: ProfileModel,
) : StateViewModel<MyItemsViewState, MyItemsViewIntents, MyItemsScreenResult>(MyItemsViewState()) {
    override val intents = MyItemsViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(MyItemsScreenResult.Finish)
        }

        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(MyItemsScreenResult.Details(isVacancy = isVacancy, id = id))
        }

        onIntent(intents.loadMore) {
            loadMore()
        }

        on(profileModel.profile) { profile ->
            val role = profile?.role ?: UserRole.None
            update { state -> state.copy(userRole = role) }
            action { state ->
                if (state.loadState == LoadState.NotStarted) {
                    load(role, reset = true)
                }
            }
        }
    }

    private fun load(role: UserRole, reset: Boolean) = when (role) {
        UserRole.Customer -> loadVacancies(reset)
        UserRole.Freelancer -> loadResumes(reset)
        UserRole.None -> Unit
    }

    private fun loadMore() {
        val state = state.value
        if (!state.hasMore || state.loadMoreState == LoadState.Loading) return

        load(state.userRole, reset = false)
    }

    private fun loadVacancies(reset: Boolean) {
        val offset = if (reset) 0 else state.value.vacancies.size

        task(
            stateRead = { vs -> if (reset) vs.loadState else vs.loadMoreState },
            stateWrite = { vs, ls -> if (reset) vs.copy(loadState = ls) else vs.copy(loadMoreState = ls) },
        ) {
            job { model.getMyVacancies(buildPagingParams(listSize = offset)) }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        vacancies = if (reset) paging.data else state.vacancies + paging.data,
                        hasMore = paging.hasMore,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun loadResumes(reset: Boolean) {
        val offset = if (reset) 0 else state.value.resumes.size

        task(
            stateRead = { vs -> if (reset) vs.loadState else vs.loadMoreState },
            stateWrite = { vs, ls -> if (reset) vs.copy(loadState = ls) else vs.copy(loadMoreState = ls) },
        ) {
            job { model.getMyResumes(buildPagingParams(listSize = offset)) }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        resumes = if (reset) paging.data else state.resumes + paging.data,
                        hasMore = paging.hasMore,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }
}
