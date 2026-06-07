package dot.adun.feature.authorized.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.authorized.data.MainRpc
import dot.adun.feature.authorized.data.api.response.GetClientActiveProjectsResponse
import dot.adun.feature.authorized.data.dto.AcceptApplicationParamsDto
import dot.adun.feature.authorized.data.dto.CancelApplicationParamsDto
import dot.adun.feature.authorized.data.dto.ApplicationDto
import dot.adun.feature.authorized.data.dto.ApplyForVacancyParamsDto
import dot.adun.feature.authorized.data.dto.CompleteProjectParamsDto
import dot.adun.feature.authorized.data.dto.GetMyApplicationsParamsDto
import dot.adun.feature.authorized.data.dto.IncomingApplicationDto
import dot.adun.feature.authorized.data.dto.LeaveProjectReviewParamsDto
import dot.adun.feature.authorized.data.dto.MyApplicationDto
import dot.adun.feature.authorized.data.dto.SearchParamsDto
import dot.adun.feature.authorized.data.dto.SearchVacancyDto
import dot.adun.feature.authorized.data.dto.VacancyDto
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.data.mappers.toNetworkModel
import dot.adun.feature.authorized.domain.entity.Application
import dot.adun.feature.authorized.domain.entity.IncomingApplication
import dot.adun.feature.authorized.domain.entity.MyApplication
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

    suspend fun searchVacancies(query: String, limit: Int, offset: Int): List<Vacancy> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.SEARCH_VACANCIES,
            parameters = SearchParamsDto(query = query, limit = limit, offset = offset)
        )
            .decodeList<SearchVacancyDto>()
            .map { it.toDomainModel() }
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

    suspend fun getMyApplications(params: PagingParams): List<MyApplication> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_MY_APPLICATIONS,
            parameters = GetMyApplicationsParamsDto(
                isActive = true,
                limit = params.limit,
                offset = params.offset,
            )
        )
            .decodeList<MyApplicationDto>()
            .map { it.toDomainModel() }
    }

    suspend fun getClientIncomingApplications(
        params: PagingParams
    ): List<IncomingApplication> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_CLIENT_INCOMING_APPLICATIONS,
            parameters = params.toNetworkModel()
        )
            .decodeList<IncomingApplicationDto>()
            .map { it.toDomainModel() }
    }

    /** Accepts an incoming application and returns the id of the created project. */
    suspend fun acceptApplication(applicationId: String): String = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { error("Project was not created") }
    ) {
        client.postgrest.rpc(
            function = MainRpc.ACCEPT_APPLICATION,
            parameters = AcceptApplicationParamsDto(applicationId = applicationId)
        )
            .decodeAs<String>()
    }

    suspend fun cancelApplication(applicationId: String) = apiRequest(
        onSuccess = {},
        onEmptyOrNull = {}
    ) {
        client.postgrest.rpc(
            function = MainRpc.CANCEL_APPLICATION,
            parameters = CancelApplicationParamsDto(applicationId = applicationId)
        )
    }

    suspend fun completeProject(projectId: String) = apiRequest(
        onSuccess = {},
        onEmptyOrNull = {}
    ) {
        client.postgrest.rpc(
            function = MainRpc.COMPLETE_PROJECT,
            parameters = CompleteProjectParamsDto(projectId = projectId)
        )
    }

    suspend fun leaveProjectReview(
        projectId: String,
        rating: Int,
        comment: String,
    ) = apiRequest(
        onSuccess = {},
        onEmptyOrNull = {}
    ) {
        client.postgrest.rpc(
            function = MainRpc.LEAVE_PROJECT_REVIEW,
            parameters = LeaveProjectReviewParamsDto(
                projectId = projectId,
                rating = rating,
                comment = comment,
            )
        )
    }
}

private const val VACANCY_TABLE = "vacancies"
