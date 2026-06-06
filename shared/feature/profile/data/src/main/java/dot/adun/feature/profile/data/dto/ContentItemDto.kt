package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Lightweight projections of the vacancy/resume rows returned inside
 * [ProfileContentWrapperDto.contentItem]. Only the fields needed to render a
 * compact card on the profile screen are decoded.
 */
@Serializable
data class ContentVacancyDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("required_skills") val requiredSkills: List<String> = emptyList(),
    @SerialName("payment_method") val paymentMethod: String = "",
    @SerialName("budget") val budget: Double = 0.0,
    @SerialName("currency") val currency: String = "",
    @SerialName("status") val status: String = "",
)

@Serializable
data class ContentResumeDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("main_skills") val mainSkills: List<String> = emptyList(),
    @SerialName("payment_preference") val paymentPreference: String = "",
    @SerialName("min_rate") val minRate: Double = 0.0,
    @SerialName("currency") val currency: String = "",
    @SerialName("is_active") val isActive: Boolean = true,
)
