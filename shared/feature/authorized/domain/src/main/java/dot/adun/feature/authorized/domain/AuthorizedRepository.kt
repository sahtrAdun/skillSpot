package dot.adun.feature.authorized.domain

import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Application
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.PagingState
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.profile.domain.entity.PublicProfile

interface AuthorizedRepository {
    suspend fun getAllVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllRecommendedVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllResumes(params: PagingParams): PagingState<Resume>
    suspend fun getAllRecommendedResumes(params: PagingParams): PagingState<Resume>
    suspend fun getUserActiveVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getClientActiveProjects(params: PagingParams): PagingState<ActiveProject>

    suspend fun getVacancyById(id: String): Vacancy
    suspend fun getResumeById(id: String): Resume
    suspend fun getProfileById(id: String): PublicProfile?
    suspend fun updateVacancy(vacancy: Vacancy)
    suspend fun updateResume(resume: Resume)

    suspend fun applyForVacancy(
        vacancyId: String,
        resumeId: String,
        coverLetter: String?,
    ): Application
}
