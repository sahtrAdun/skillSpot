package dot.adun.feature.chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageParamsDto(
    @SerialName("p_project_id") val projectId: String,
    @SerialName("p_content") val content: String,
)

@Serializable
data class GetMessagesParamsDto(
    @SerialName("p_project_id") val projectId: String,
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int,
)
