package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileParamsDto(
    @SerialName("p_profile_id") val profileId: String
)