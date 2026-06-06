package dot.adun.feature.authorized.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.authorized.data.MainRpc
import dot.adun.feature.authorized.data.api.response.GetClientActiveProjectsResponse
import dot.adun.feature.authorized.data.dto.ApplicationDto
import dot.adun.feature.authorized.data.dto.ApplyForVacancyParamsDto
import dot.adun.feature.authorized.data.dto.VacancyDto
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.data.mappers.toNetworkModel
import dot.adun.feature.authorized.domain.entity.Application
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.Vacancy
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
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

    suspend fun getVacancyById(id: String): Vacancy? = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { null }
    ) {
        client.from(VACANCY_TABLE)
            .select { filter { VacancyDto::id eq id } }
            .decodeList<VacancyDto>()
            .firstOrNull()
            ?.toDomainModel()
    }

    suspend fun updateVacancy(vacancy: VacancyDto) = apiRequest(
        onSuccess = {},
        onEmptyOrNull = {}
    ) {
        client.from(VACANCY_TABLE)
            .update(vacancy) {
                filter { VacancyDto::id eq vacancy.id }
            }
    }

    suspend fun applyForVacancy(
        vacancyId: String,
        resumeId: String,
        coverLetter: String?,
    ): Application = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { error("Application was not created") }
    ) {
        client.postgrest.rpc(
            function = MainRpc.APPLY_FOR_VACANCY,
            parameters = ApplyForVacancyParamsDto(
                vacancyId = vacancyId,
                resumeId = resumeId,
                coverLetter = coverLetter,
            )
        )
            .decodeList<ApplicationDto>()
            .firstOrNull()
            ?.toDomainModel()
    }
}

private const val VACANCY_TABLE = "vacancies"
