package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    @SerialName("id") val id: String,
    @SerialName("status") val status: String,
    @SerialName("freelancer_id") val freelancerId: String? = null,
)
