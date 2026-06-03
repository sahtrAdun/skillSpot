package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicProfileDto(
    @SerialName("id")
    val id: String,
    @SerialName("role")
    val role: String,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("bio")
    val bio: String? = null,
    @SerialName("rating_avg")
    val ratingAvg: Float? = null,
    @SerialName("tasks_completed")
    val tasksCompleted: Int = 0,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("age")
    val age: Short? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("created_at")
    val createdAt: String,
)