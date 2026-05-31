package dot.adun.feature.authorized.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.authorized.data.MainRpc
import dot.adun.feature.authorized.data.api.response.GetClientActiveProjectsResponse
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
class VacanciesApi @Inject constructor(
    private val client: SupabaseClient
) {
    suspend fun getAllRecommendedVacancies(params: PagingParams): List<Vacancy> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_MATCHING_VACANCIES,
            parameters = params.toNetworkModel()
        )
            .decodeList<VacancyDto>()
            .map { it.toDomainModel() }
    }

    suspend fun getUserActiveVacancies(params: PagingParams): List<Vacancy> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_ACTIVE_WORKING_VACANCIES,
            parameters = params.toNetworkModel()
        )
            .decodeList<VacancyDto>()
            .map { it.toDomainModel() }
    }

    suspend fun getClientActiveProjects(params: PagingParams): GetClientActiveProjectsResponse = apiRequest(
        onSuccess = { GetClientActiveProjectsResponse.Ok(it) },
        onEmptyOrNull = { GetClientActiveProjectsResponse.NotFound }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_CLIENT_ACTIVE_PROJECTS,
            parameters = params.toNetworkModel()
        )
            .decodeList<GetClientActiveProjectsResponse.Value>()
    }
}
