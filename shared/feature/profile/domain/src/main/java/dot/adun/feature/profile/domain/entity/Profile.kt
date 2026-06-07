package dot.adun.feature.profile.domain.entity

import dot.adun.core.domain.entity.UserRole
import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Profile(
    val id: String,
    val userId: String,
    val role: UserRole,
    val email: String?,
    val personalInfo: PersonalInfo,
    val ratingAvg: Float?,
    val tasksCompleted: Int,
    val avatarUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {
    @Immutable
    data class PersonalInfo(
        val fullName: String?,
        val bio: String?,
        val age: Short?,
        val country: String?,
        val city: String?
    )
}
