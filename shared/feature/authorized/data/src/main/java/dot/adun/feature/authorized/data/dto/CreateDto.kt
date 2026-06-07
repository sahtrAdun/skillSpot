package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateVacancyParamsDto(
    @SerialName("p_title") val title: String,
    @SerialName("p_description") val description: String,
    @SerialName("p_required_skills") val requiredSkills: List<String>,
    @SerialName("p_experience_years_required") val experienceYearsRequired: Int?,
    @SerialName("p_payment_method") val paymentMethod: String,
    @SerialName("p_budget") val budget: Double,
    @SerialName("p_currency") val currency: String,
    @SerialName("p_duration_type") val durationType: String,
)

@Serializable
data class CreateResumeParamsDto(
    @SerialName("p_title") val title: String,
    @SerialName("p_bio") val bio: String?,
    @SerialName("p_github_url") val githubUrl: String?,
    @SerialName("p_portfolio_url") val portfolioUrl: String?,
    @SerialName("p_main_skills") val mainSkills: List<String>,
    @SerialName("p_secondary_skills") val secondarySkills: List<String>?,
    @SerialName("p_payment_preference") val paymentPreference: String,
    @SerialName("p_min_rate") val minRate: Double,
    @SerialName("p_currency") val currency: String,
    @SerialName("p_availability") val availability: List<String>,
)
