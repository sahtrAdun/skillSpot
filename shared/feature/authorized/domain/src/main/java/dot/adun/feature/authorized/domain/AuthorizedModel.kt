package dot.adun.feature.authorized.domain

import dot.adun.feature.authorized.domain.entity.PagingParams
import javax.inject.Inject

class AuthorizedModel @Inject constructor(
    private val repository: AuthorizedRepository
) {
    suspend fun getAllVacancies(params: PagingParams) =
        repository.getAllVacancies(params)

    suspend fun getAllRecommendedVacancies(params: PagingParams) =
        repository.getAllRecommendedVacancies(params)

    suspend fun getAllResumes(params: PagingParams) =
        repository.getAllResumes(params)

    suspend fun getAllRecommendedResumes(params: PagingParams) =
        repository.getAllRecommendedResumes(params)

    suspend fun getUserActiveVacancies(params: PagingParams) =
        repository.getUserActiveVacancies(params)

    suspend fun getClientActiveProjects(params: PagingParams) =
        repository.getClientActiveProjects(params)
}
