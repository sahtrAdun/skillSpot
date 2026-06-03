package dot.adun.feature.authorized.ui.screen.details.vacancy

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.domain.entity.Vacancy

@Immutable
data class VacancyDetailsViewState(
    val vacancy: Vacancy? = null,
    val creator: PublicProfile? = null,
    val currentUserId: String? = null,
    val loadState: LoadState = LoadState.NotStarted,
    val creatorLoadState: LoadState = LoadState.NotStarted,
) {
    fun emptyForId(): Boolean = vacancy == null

    val canEdit: Boolean
        get() = currentUserId != null && vacancy?.clientId == currentUserId
}

sealed interface VacancyDetailsScreenResult {
    data object Finish : VacancyDetailsScreenResult

    @Immutable
    data class Edit(val id: String) : VacancyDetailsScreenResult
}
