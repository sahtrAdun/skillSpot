package dot.adun.feature.authorized.domain

import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.PagingState
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import kotlinx.coroutines.flow.Flow

interface AuthorizedRepository {
    suspend fun getAllVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllRecommendedVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getAllResumes(params: PagingParams): PagingState<Resume>
    suspend fun getAllRecommendedResumes(params: PagingParams): PagingState<Resume>
    suspend fun getUserActiveVacancies(params: PagingParams): PagingState<Vacancy>
    suspend fun getClientActiveProjects(params: PagingParams): PagingState<ActiveProject>
}
