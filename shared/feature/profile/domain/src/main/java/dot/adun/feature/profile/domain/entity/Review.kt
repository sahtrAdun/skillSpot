package dot.adun.feature.profile.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Review(
    val id: String,
    val projectId: String,
    val rating: Int,
    val comment: String?,
    val createdAt: LocalDateTime,
    val senderId: String,
    val senderName: String?,
    val senderAvatar: String?,
)
