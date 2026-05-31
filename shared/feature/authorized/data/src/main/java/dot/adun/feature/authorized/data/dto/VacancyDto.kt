package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    @SerialName("id")
    val id: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("required_skills")
    val requiredSkills: List<String>,
    @SerialName("experience_years_required")
    val experienceYearsRequired: Int? = 0,
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("budget")
    val budget: Double,
    @SerialName("currency")
    val currency: String,
    @SerialName("duration_type")
    val durationType: String,
    @SerialName("status")
    val status: String = "OPEN",
    @SerialName("views_count")
    val viewsCount: Int = 0,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)
