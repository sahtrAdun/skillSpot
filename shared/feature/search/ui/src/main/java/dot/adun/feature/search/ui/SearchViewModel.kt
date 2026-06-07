package dot.adun.feature.search.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.buildPagingParams
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@Stable
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val model: AuthorizedModel,
    private val profileModel: ProfileModel,
) : StateViewModel<SearchViewState, SearchViewIntents, SearchScreenResult>(SearchViewState()) {
    override val intents = SearchViewIntents()

    init {
        onIntent(intents.cancel) {
            emitResult(SearchScreenResult.Finish)
        }

        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(SearchScreenResult.Details(isVacancy = isVacancy, id = id))
        }

        on(profileModel.profile) { profile ->
            update { state -> state.copy(userRole = profile?.role ?: UserRole.None) }
        }

        onIntent(intents.updateQuery) { query ->
            update { state -> state.copy(query = query) }
        }

        onIntent(intents.loadMore) {
            loadMore()
        }

        600.milliseconds.debounceOn(intent(intents.updateQuery)) { query ->
            search(query)
        }
    }

    private fun search(query: String) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) {
            update { state ->
                state.copy(
                    vacancies = emptyList(),
                    resumes = emptyList(),
                    hasMore = false,
                    loadState = LoadState.NotStarted,
                    loadMoreState = LoadState.NotStarted,
                )
            }
            return
        }

        when (state.value.userRole) {
            UserRole.Freelancer -> searchVacancies(trimmed, reset = true)
            UserRole.Customer -> searchResumes(trimmed, reset = true)
            UserRole.None -> Unit
        }
    }

    private fun loadMore() {
        val state = state.value
        if (!state.hasMore || state.loadMoreState == LoadState.Loading) return

        val query = state.query.trim()
        when (state.userRole) {
            UserRole.Freelancer -> searchVacancies(query, reset = false)
            UserRole.Customer -> searchResumes(query, reset = false)
            UserRole.None -> Unit
        }
    }

    private fun searchVacancies(query: String, reset: Boolean) {
        if (query.isBlank()) return
        val offset = if (reset) 0 else state.value.vacancies.size

        task(
            stateRead = { vs -> if (reset) LoadState.NotStarted else vs.loadMoreState },
            stateWrite = { vs, ls -> if (reset) vs.copy(loadState = ls) else vs.copy(loadMoreState = ls) },
        ) {
            job { model.searchVacancies(query, buildPagingParams(listSize = offset)) }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        vacancies = if (reset) paging.data else state.vacancies + paging.data,
                        resumes = emptyList(),
                        hasMore = paging.hasMore,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun searchResumes(query: String, reset: Boolean) {
        if (query.isBlank()) return
        val offset = if (reset) 0 else state.value.resumes.size

        task(
            stateRead = { vs -> if (reset) LoadState.NotStarted else vs.loadMoreState },
            stateWrite = { vs, ls -> if (reset) vs.copy(loadState = ls) else vs.copy(loadMoreState = ls) },
        ) {
            job { model.searchResumes(query, buildPagingParams(listSize = offset, pageSize = 5)) }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        resumes = if (reset) paging.data else state.resumes + paging.data,
                        vacancies = emptyList(),
                        hasMore = paging.hasMore,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }
}

sealed interface SearchScreenResult {
    data object Finish : SearchScreenResult

    @Immutable
    data class Details(val isVacancy: Boolean, val id: String) : SearchScreenResult
}
