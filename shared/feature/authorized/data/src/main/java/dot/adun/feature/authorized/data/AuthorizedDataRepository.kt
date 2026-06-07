package dot.adun.feature.authorized.data

import dot.adun.feature.authorized.data.api.ResumesApi
import dot.adun.feature.authorized.data.api.VacanciesApi
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.data.mappers.toNetworkModel
import dot.adun.feature.authorized.domain.AuthorizedRepository
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Application
import dot.adun.feature.authorized.domain.entity.IncomingApplication
import dot.adun.feature.authorized.domain.entity.MyApplication
import dot.adun.feature.authorized.domain.entity.NewResume
import dot.adun.feature.authorized.domain.entity.NewVacancy
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.PagingState
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.profile.data.api.ProfileApi
import dot.adun.feature.profile.domain.entity.PublicProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizedDataRepository @Inject constructor(
    private val vacanciesApi: VacanciesApi,
    private val resumesApi: ResumesApi,
    private val profileApi: ProfileApi,
    private val realtime: ProjectRealtimeDataSource,
) : AuthorizedRepository {
    override suspend fun getAllVacancies(params: PagingParams): PagingState<Vacancy> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecommendedVacancies(params: PagingParams): PagingState<Vacancy> {
        val response = vacanciesApi.getAllRecommendedVacancies(params)

        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size)
        )
    }

    override suspend fun getAllResumes(params: PagingParams): PagingState<Resume> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecommendedResumes(params: PagingParams): PagingState<Resume> {
        val response = resumesApi.getAllRecommendedResumes(params)

        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size)
        )
    }

    override suspend fun getUserActiveVacancies(params: PagingParams): PagingState<Vacancy> {
        val response = vacanciesApi.getUserActiveVacancies(params)

        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size)
        )
    }

    override suspend fun getClientActiveProjects(params: PagingParams): PagingState<ActiveProject> {
        val response = vacanciesApi
            .getClientActiveProjects(params)
            .toDomainModel()

        return if (response.isEmpty()) PagingState.empty() else PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size),
        )
    }

    override suspend fun getVacancyById(id: String): Vacancy {
        return vacanciesApi.getVacancyById(id) ?: error("Vacancy not found")
    }

    override suspend fun getResumeById(id: String): Resume {
        return resumesApi.getResumeById(id) ?: error("Resume not found")
    }

    override suspend fun searchVacancies(query: String, params: PagingParams): PagingState<Vacancy> {
        val response = vacanciesApi.searchVacancies(query, params.limit, params.offset)
        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size, params.limit),
        )
    }

    override suspend fun searchResumes(query: String, params: PagingParams): PagingState<Resume> {
        val response = resumesApi.searchResumes(query, params.limit, params.offset)
        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size, params.limit),
        )
    }

    override suspend fun getMyVacancies(params: PagingParams): PagingState<Vacancy> {
        val response = vacanciesApi.getMyVacancies(params)
        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size, params.limit),
        )
    }

    override suspend fun getMyResumes(params: PagingParams): PagingState<Resume> {
        val response = resumesApi.getMyResumes(params)
        return PagingState(
            data = response,
            hasMore = PagingState.hasMore(response.size, params.limit),
        )
    }

    override suspend fun createVacancy(draft: NewVacancy): String {
        return vacanciesApi.createVacancy(draft)
    }

    override suspend fun createResume(draft: NewResume): String {
        return resumesApi.createResume(draft)
    }

    override suspend fun getProfileById(id: String): PublicProfile? {
        return profileApi.getProfileById(id)
    }

    override suspend fun updateVacancy(vacancy: Vacancy) {
        vacanciesApi.updateVacancy(vacancy.toNetworkModel())
    }

    override suspend fun updateResume(resume: Resume) {
        resumesApi.updateResume(resume.toNetworkModel())
    }

    override suspend fun applyForVacancy(
        vacancyId: String,
        resumeId: String,
        coverLetter: String?,
    ): Application {
        return vacanciesApi.applyForVacancy(vacancyId, resumeId, coverLetter)
    }

    override suspend fun getMyApplications(params: PagingParams): List<MyApplication> {
        return vacanciesApi.getMyApplications(params)
    }

    override suspend fun getClientIncomingApplications(
        params: PagingParams
    ): List<IncomingApplication> {
        return vacanciesApi.getClientIncomingApplications(params)
    }

    override suspend fun acceptApplication(applicationId: String): String {
        return vacanciesApi.acceptApplication(applicationId)
    }

    override suspend fun cancelApplication(applicationId: String) {
        vacanciesApi.cancelApplication(applicationId)
    }

    override suspend fun completeProject(projectId: String) {
        vacanciesApi.completeProject(projectId)
    }

    override suspend fun leaveProjectReview(projectId: String, rating: Int, comment: String) {
        vacanciesApi.leaveProjectReview(projectId, rating, comment)
    }

    override fun observeCompletedProjects(freelancerId: String): Flow<String> {
        return realtime.observeCompletedProjects(freelancerId)
    }
}
