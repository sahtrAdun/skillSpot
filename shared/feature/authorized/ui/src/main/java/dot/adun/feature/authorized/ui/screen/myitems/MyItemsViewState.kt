package dot.adun.feature.authorized.ui.screen.myitems

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class MyItemsViewState(
    val userRole: UserRole = UserRole.None,
    val vacancies: List<Vacancy> = emptyList(),
    val resumes: List<Resume> = emptyList(),
    val hasMore: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val loadMoreState: LoadState = LoadState.NotStarted,
) {
    val isEmptyResult: Boolean
        get() = loadState == LoadState.Done && vacancies.isEmpty() && resumes.isEmpty()
}

sealed interface MyItemsScreenResult {
    data object Finish : MyItemsScreenResult

    @Immutable
    data class Details(val isVacancy: Boolean, val id: String) : MyItemsScreenResult
}
