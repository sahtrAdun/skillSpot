package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMyProfileParamsDto(
    @SerialName("p_full_name") val fullName: String?,
    @SerialName("p_bio") val bio: String?,
    @SerialName("p_age") val age: Int?,
    @SerialName("p_country") val country: String?,
    @SerialName("p_city") val city: String?,
    @SerialName("p_avatar_url") val avatarUrl: String?,
)
