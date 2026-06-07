package dot.adun.feature.authorized.ui.screen.applications

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.authorized.domain.entity.IncomingApplication
import dot.adun.feature.authorized.domain.entity.MyApplication

@Immutable
data class ApplicationsViewState(
    val userRole: UserRole = UserRole.None,
    val myApplications: List<MyApplication> = emptyList(),
    val incomingApplications: List<IncomingApplication> = emptyList(),
    val loadState: LoadState = LoadState.NotStarted,
    val acceptState: LoadState = LoadState.NotStarted,
    val approvingId: String? = null,
    val cancelState: LoadState = LoadState.NotStarted,
)

sealed interface ApplicationsScreenResult {
    data object Finish : ApplicationsScreenResult

    @Immutable
    data class Details(val isVacancy: Boolean, val id: String) : ApplicationsScreenResult

    @Immutable
    data class OpenProject(val projectId: String) : ApplicationsScreenResult
}
