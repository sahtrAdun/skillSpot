package dot.adun.feature.authorized.data.api.response

import dot.adun.feature.authorized.data.dto.ResumeDto
import dot.adun.feature.authorized.data.dto.VacancyDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface GetClientActiveProjectsResponse {
    data class Ok(
        val values: List<Value>
    ) : GetClientActiveProjectsResponse

    data object NotFound : GetClientActiveProjectsResponse

    @Serializable
    data class Value(
        @SerialName("vacancy") val vacancy: VacancyDto,
        @SerialName("executor_resume") val executorResume: ResumeDto
    )
}


