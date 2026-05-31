package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class ActiveViewState(
    val userRole: UserRole = UserRole.None,
    val loadState: LoadState = LoadState.NotStarted,
    val vacancies: List<Vacancy> = emptyList(),
    val activeProjects: List<ActiveProject> = emptyList(),
) {
    fun emptyForUser(): Boolean = when (userRole) {
        UserRole.Freelancer -> vacancies.isEmpty()
        UserRole.Customer -> activeProjects.isEmpty()
        UserRole.None -> true
    }
}
