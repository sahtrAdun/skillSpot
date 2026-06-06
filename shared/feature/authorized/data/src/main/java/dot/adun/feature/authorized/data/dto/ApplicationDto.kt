package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDto(
    @SerialName("id") val id: String,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("resume_id") val resumeId: String,
    @SerialName("initiator") val initiator: String,
    @SerialName("status") val status: String = "PENDING",
    @SerialName("cover_letter") val coverLetter: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)
