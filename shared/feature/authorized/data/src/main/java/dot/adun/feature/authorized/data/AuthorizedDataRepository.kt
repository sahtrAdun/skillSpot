package dot.adun.feature.authorized.data

import dot.adun.core.domain.util.mapUntilChanged
import dot.adun.feature.authorized.data.api.VacanciesApi
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.domain.AuthorizedRepository
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.PagingState
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.domain.entity.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizedDataRepository @Inject constructor(
    private val vacanciesApi: VacanciesApi,
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
        TODO("Not yet implemented")
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
}
