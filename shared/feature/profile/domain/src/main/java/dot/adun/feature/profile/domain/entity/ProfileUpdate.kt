package dot.adun.feature.profile.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class ProfileUpdate(
    val fullName: String?,
    val bio: String?,
    val age: Short?,
    val country: String?,
    val city: String?,
    val avatarUrl: String?,
)
