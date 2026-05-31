package dot.adun.feature.authorized.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.authorized.data.MainRpc
import dot.adun.feature.authorized.data.dto.ResumeDto
import dot.adun.feature.authorized.data.dto.VacancyDto
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.data.mappers.toNetworkModel
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumesApi @Inject constructor(
    private val client: SupabaseClient
) {
    suspend fun getAllRecommendedResumes(params: PagingParams): List<Resume> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_MATCHING_RESUMES,
            parameters = params.toNetworkModel()
        )
            .decodeList<ResumeDto>()
            .map { it.toDomainModel() }
    }
}
