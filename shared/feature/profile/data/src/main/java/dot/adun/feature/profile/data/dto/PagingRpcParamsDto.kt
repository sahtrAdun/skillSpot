package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetReviewsParamsDto(
    @SerialName("p_profile_id") val profileId: String,
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int,
)

@Serializable
data class GetContentParamsDto(
    @SerialName("p_profile_id") val profileId: String,
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int,
)
