package dot.adun.feature.authorized.domain

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
import dot.adun.feature.profile.domain.entity.PublicProfile
import kotlinx.coroutines.flow.Flow

interface AuthorizedRepository {
    suspend fun getAllVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllRecommendedVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllResumes(params: PagingParams): PagingState<Resume>
    suspend fun getAllRecommendedResumes(params: PagingParams): PagingState<Resume>
    suspend fun getUserActiveVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getClientActiveProjects(params: PagingParams): PagingState<ActiveProject>

    suspend fun getVacancyById(id: String): Vacancy
    suspend fun getResumeById(id: String): Resume
    suspend fun searchVacancies(query: String, params: PagingParams): PagingState<Vacancy>
    suspend fun searchResumes(query: String, params: PagingParams): PagingState<Resume>
    suspend fun getMyVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getMyResumes(params: PagingParams): PagingState<Resume>
    suspend fun createVacancy(draft: NewVacancy): String
    suspend fun createResume(draft: NewResume): String
    suspend fun getProfileById(id: String): PublicProfile?
    suspend fun updateVacancy(vacancy: Vacancy)
    suspend fun updateResume(resume: Resume)

    suspend fun applyForVacancy(
        vacancyId: String,
        resumeId: String,
        coverLetter: String?,
    ): Application

    suspend fun getMyApplications(params: PagingParams): List<MyApplication>
    suspend fun getClientIncomingApplications(params: PagingParams): List<IncomingApplication>
    suspend fun acceptApplication(applicationId: String): String
    suspend fun cancelApplication(applicationId: String)
    suspend fun completeProject(projectId: String)
    suspend fun leaveProjectReview(projectId: String, rating: Int, comment: String)
    fun observeCompletedProjects(freelancerId: String): Flow<String>
}
