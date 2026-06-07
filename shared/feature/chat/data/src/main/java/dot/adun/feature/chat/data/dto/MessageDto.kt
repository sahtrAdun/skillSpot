package dot.adun.feature.chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Shared shape for both the `get_project_messages` RPC (which provides sender
 * name/avatar via a join) and raw realtime `messages` rows (where those joined
 * fields are absent — hence nullable with defaults).
 */
@Serializable
data class MessageDto(
    @SerialName("id") val id: String,
    @SerialName("project_id") val projectId: String,
    @SerialName("sender_id") val senderId: String,
    @SerialName("sender_name") val senderName: String? = null,
    @SerialName("sender_avatar_url") val senderAvatarUrl: String? = null,
    @SerialName("content") val content: String,
    @SerialName("created_at") val createdAt: String,
)
