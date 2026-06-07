package dot.adun.feature.chat.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Message(
    val id: String,
    val projectId: String,
    val senderId: String,
    val senderName: String?,
    val senderAvatarUrl: String?,
    val content: String,
    val createdAt: LocalDateTime,
)
