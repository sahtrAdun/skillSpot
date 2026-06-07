package dot.adun.feature.profile.ui.screen

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.profile.domain.entity.Review

enum class ProfileTab { Info, Reviews, Content }

@Immutable
data class ProfileViewState(
    val profile: PublicProfile? = null,
    val reviews: List<Review> = emptyList(),
    val content: ProfileContent = ProfileContent.Empty,
    val selectedTab: ProfileTab = ProfileTab.Info,
    val isOwnProfile: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val reviewsLoadState: LoadState = LoadState.NotStarted,
    val contentLoadState: LoadState = LoadState.NotStarted,
)

sealed interface ProfileScreenResult {
    data object Finish : ProfileScreenResult
    data object Logout : ProfileScreenResult
    data object EditProfile : ProfileScreenResult
    data class Details(val isVacancy: Boolean, val id: String) : ProfileScreenResult
    data class OtherProfile(val profileId: String) : ProfileScreenResult
}
