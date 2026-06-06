package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewWithSenderDto(
    @SerialName("review_id") val reviewId: String,
    @SerialName("project_id") val projectId: String,
    val rating: Short,
    val comment: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("sender_id") val senderId: String,
    @SerialName("sender_name") val senderName: String? = null,
    @SerialName("sender_avatar") val senderAvatar: String? = null,
)
