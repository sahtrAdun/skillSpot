package dot.adun.feature.home.ui.screen

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class HomeViewState(
    val loadState: LoadState = LoadState.NotStarted,
    val userRole: UserRole = UserRole.None,
    val recommendedVacancies: List<Vacancy> = emptyList(),
    val recommendedResumes: List<Resume> = emptyList(),
    val refreshing: Boolean = false
) {
    fun emptyForUser(): Boolean = when (userRole) {
        UserRole.Freelancer -> recommendedVacancies.isEmpty()
        UserRole.Customer -> recommendedResumes.isEmpty()
        UserRole.None -> true
    }

    val itemsSize: Int = when (userRole) {
        UserRole.Freelancer -> recommendedVacancies.size
        UserRole.Customer -> recommendedResumes.size
        UserRole.None -> 0
    }
}
