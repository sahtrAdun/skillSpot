package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class ActiveViewState(
    val userRole: UserRole = UserRole.None,
    val currentUserId: String? = null,
    val loadState: LoadState = LoadState.NotStarted,
    val vacancies: List<Vacancy> = emptyList(),
    val activeProjects: List<ActiveProject> = emptyList(),
    val completingProjectId: String? = null,
    val completeState: LoadState = LoadState.NotStarted,
    /** The project a review is being left for; non-null shows the review dialog. */
    val reviewProjectId: String? = null,
    val reviewState: LoadState = LoadState.NotStarted,
) {
    fun emptyForUser(): Boolean = when (userRole) {
        UserRole.Freelancer -> vacancies.isEmpty()
        UserRole.Customer -> activeProjects.isEmpty()
        UserRole.None -> true
    }
}

@Immutable
data class ReviewArgs(
    val projectId: String,
    val rating: Int,
    val comment: String,
)
