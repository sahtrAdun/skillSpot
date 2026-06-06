package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplyForVacancyParamsDto(
    @SerialName("p_vacancy_id") val vacancyId: String,
    @SerialName("p_resume_id") val resumeId: String,
    @SerialName("p_cover_letter") val coverLetter: String? = null,
)
