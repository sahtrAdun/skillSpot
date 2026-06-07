package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteProjectParamsDto(
    @SerialName("p_project_id") val projectId: String,
)

@Serializable
data class LeaveProjectReviewParamsDto(
    @SerialName("p_project_id") val projectId: String,
    @SerialName("p_rating") val rating: Int,
    @SerialName("p_comment") val comment: String,
)
