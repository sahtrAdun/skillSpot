package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.feature.profile.domain.entity.ProfileResume
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class VacancyDetailsViewState(
    val vacancy: Vacancy? = null,
    val creator: PublicProfile? = null,
    val currentUserId: String? = null,
    val currentUserRole: UserRole? = null,
    val resumes: List<ProfileResume> = emptyList(),
    val applied: Boolean = false,
    val showApplySheet: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val creatorLoadState: LoadState = LoadState.NotStarted,
    val resumesLoadState: LoadState = LoadState.NotStarted,
    val applyState: LoadState = LoadState.NotStarted,
) {
    fun emptyForId(): Boolean = vacancy == null

    val canEdit: Boolean
        get() = currentUserId != null && vacancy?.clientId == currentUserId

    /** A freelancer may apply only while the vacancy is open. */
    val canApply: Boolean
        get() = currentUserRole == UserRole.Freelancer &&
            vacancy?.status == Vacancy.Status.Open
}

@Immutable
data class ApplyArgs(
    val resumeId: String,
    val coverLetter: String?,
)

sealed interface VacancyDetailsScreenResult {
    data object Finish : VacancyDetailsScreenResult

    @Immutable
    data class Edit(val id: String) : VacancyDetailsScreenResult

    @Immutable
    data class OpenAuthorProfile(val profileId: String) : VacancyDetailsScreenResult
}
