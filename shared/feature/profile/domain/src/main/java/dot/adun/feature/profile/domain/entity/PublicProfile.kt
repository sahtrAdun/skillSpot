package dot.adun.feature.profile.domain.entity

import dot.adun.core.domain.entity.UserRole
import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class PublicProfile(
    val id: String,
    val role: UserRole,
    val fullName: String?,
    val bio: String?,
    val ratingAvg: Float?,
    val tasksCompleted: Int,
    val avatarUrl: String?,
    val age: Short?,
    val country: String?,
    val city: String?,
    val createdAt: LocalDateTime,
)