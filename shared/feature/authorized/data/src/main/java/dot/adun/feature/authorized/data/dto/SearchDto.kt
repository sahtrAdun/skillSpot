package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchParamsDto(
    @SerialName("p_query") val query: String,
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int,
)

@Serializable
data class SearchVacancyDto(
    @SerialName("id") val id: String,
    @SerialName("client_id") val clientId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String = "",
    @SerialName("required_skills") val requiredSkills: List<String> = emptyList(),
    @SerialName("experience_years_required") val experienceYearsRequired: Int? = null,
    @SerialName("payment_method") val paymentMethod: String,
    @SerialName("budget") val budget: Double = 0.0,
    @SerialName("currency") val currency: String,
    @SerialName("duration_type") val durationType: String? = null,
    @SerialName("status") val status: String = "OPEN",
    @SerialName("views_count") val viewsCount: Int = 0,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class SearchResumeDto(
    @SerialName("id") val id: String,
    @SerialName("freelancer_id") val freelancerId: String,
    @SerialName("title") val title: String,
    @SerialName("bio") val bio: String? = null,
    @SerialName("github_url") val githubUrl: String? = null,
    @SerialName("portfolio_url") val portfolioUrl: String? = null,
    @SerialName("main_skills") val mainSkills: List<String> = emptyList(),
    @SerialName("secondary_skills") val secondarySkills: List<String>? = null,
    @SerialName("payment_preference") val paymentPreference: String,
    @SerialName("min_rate") val minRate: Double = 0.0,
    @SerialName("currency") val currency: String,
    @SerialName("availability") val availability: List<String> = emptyList(),
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("created_at") val createdAt: String,
)
