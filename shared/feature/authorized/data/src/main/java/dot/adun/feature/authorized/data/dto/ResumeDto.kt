package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumeDto(
    @SerialName("id")
    val id: String,
    @SerialName("freelancer_id")
    val freelancerId: String,
    @SerialName("title")
    val title: String,
    @SerialName("bio")
    val bio: String? = null,
    @SerialName("github_url")
    val githubUrl: String? = null,
    @SerialName("portfolio_url")
    val portfolioUrl: String? = null,
    @SerialName("main_skills")
    val mainSkills: List<String>,
    @SerialName("secondary_skills")
    val secondarySkills: List<String>? = null,
    @SerialName("payment_preference")
    val paymentPreference: String,
    @SerialName("min_rate")
    val minRate: Double,
    @SerialName("currency")
    val currency: String,
    @SerialName("availability")
    val availability: List<String>,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)
