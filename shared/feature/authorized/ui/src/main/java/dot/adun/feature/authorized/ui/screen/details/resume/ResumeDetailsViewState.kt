package dot.adun.feature.authorized.ui.screen.details.resume

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.domain.entity.Resume

@Immutable
data class ResumeDetailsViewState(
    val resume: Resume? = null,
    val creator: PublicProfile? = null,
    val currentUserId: String? = null,
    val loadState: LoadState = LoadState.NotStarted,
    val creatorLoadState: LoadState = LoadState.NotStarted,
) {
    val canEdit: Boolean
        get() = currentUserId != null && resume?.freelancerId == currentUserId
}

sealed interface ResumeDetailsScreenResult {
    data object Finish : ResumeDetailsScreenResult

    @Immutable
    data class Edit(val id: String) : ResumeDetailsScreenResult
}
