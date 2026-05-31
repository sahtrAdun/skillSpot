package dot.adun.feature.profile.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface ProfileResult {
    data object UserNotFound : ProfileResult
    data object UpdateFailed : ProfileResult
    data object UpdateSuccess : ProfileResult

    @Immutable
    data class SuccessFetch(val profile: Profile) : ProfileResult
}