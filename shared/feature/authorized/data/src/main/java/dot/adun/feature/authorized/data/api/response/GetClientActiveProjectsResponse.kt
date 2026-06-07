package dot.adun.feature.authorized.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface GetClientActiveProjectsResponse {
    data class Ok(
        val values: List<Value>
    ) : GetClientActiveProjectsResponse

    data object NotFound : GetClientActiveProjectsResponse

    @Serializable
    data class Value(
        @SerialName("project_id") val projectId: String,
        @SerialName("project_status") val projectStatus: String,
        @SerialName("project_created_at") val projectCreatedAt: String,
        @SerialName("vacancy_id") val vacancyId: String,
        @SerialName("vacancy_title") val vacancyTitle: String,
        @SerialName("vacancy_budget") val vacancyBudget: Double = 0.0,
        @SerialName("vacancy_currency") val vacancyCurrency: String,
        @SerialName("freelancer_id") val freelancerId: String,
        @SerialName("freelancer_name") val freelancerName: String? = null,
        @SerialName("freelancer_avatar") val freelancerAvatar: String? = null,
        @SerialName("resume_id") val resumeId: String,
        @SerialName("resume_title") val resumeTitle: String,
    )
}


