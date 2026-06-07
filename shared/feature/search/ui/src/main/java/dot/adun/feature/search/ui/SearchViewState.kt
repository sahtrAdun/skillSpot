package dot.adun.feature.search.ui

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class SearchViewState(
    val query: String = "",
    val userRole: UserRole = UserRole.None,
    val vacancies: List<Vacancy> = emptyList(),
    val resumes: List<Resume> = emptyList(),
    val hasMore: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val loadMoreState: LoadState = LoadState.NotStarted,
) {
    val isEmptyResult: Boolean
        get() = query.isNotBlank() &&
            loadState == LoadState.Done &&
            vacancies.isEmpty() &&
            resumes.isEmpty()
}
