package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyApplicationDto(
    @SerialName("application_id") val applicationId: String,
    @SerialName("application_status") val applicationStatus: String,
    @SerialName("initiator") val initiator: String,
    @SerialName("cover_letter") val coverLetter: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("vacancy_title") val vacancyTitle: String,
    @SerialName("vacancy_budget") val vacancyBudget: Double = 0.0,
    @SerialName("vacancy_currency") val vacancyCurrency: String,
    @SerialName("resume_id") val resumeId: String,
    @SerialName("resume_title") val resumeTitle: String,
    @SerialName("partner_full_name") val partnerFullName: String? = null,
    @SerialName("partner_avatar_url") val partnerAvatarUrl: String? = null,
)

@Serializable
data class IncomingApplicationDto(
    @SerialName("application_id") val applicationId: String,
    @SerialName("application_status") val applicationStatus: String,
    @SerialName("cover_letter") val coverLetter: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("vacancy_title") val vacancyTitle: String,
    @SerialName("resume_id") val resumeId: String,
    @SerialName("freelancer_name") val freelancerName: String? = null,
    @SerialName("freelancer_avatar") val freelancerAvatar: String? = null,
    @SerialName("resume_title") val resumeTitle: String,
    @SerialName("resume_skills") val resumeSkills: List<String> = emptyList(),
)
