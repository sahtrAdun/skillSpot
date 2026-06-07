package dot.adun.feature.authorized.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.authorized.data.MainRpc
import dot.adun.feature.authorized.data.dto.CreateResumeParamsDto
import dot.adun.feature.authorized.data.dto.ResumeDto
import dot.adun.feature.authorized.data.dto.SearchParamsDto
import dot.adun.feature.authorized.data.dto.SearchResumeDto
import dot.adun.feature.authorized.data.mappers.toDomainModel
import dot.adun.feature.authorized.data.mappers.toNetworkModel
import dot.adun.feature.authorized.data.mappers.toNetworkValue
import dot.adun.feature.authorized.domain.entity.NewResume
import dot.adun.feature.authorized.domain.entity.PagingParams
import dot.adun.feature.authorized.domain.entity.Resume
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
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

    suspend fun searchResumes(query: String, limit: Int, offset: Int): List<Resume> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.SEARCH_RESUMES,
            parameters = SearchParamsDto(query = query, limit = limit, offset = offset)
        )
            .decodeList<SearchResumeDto>()
            .map { it.toDomainModel() }
    }

    suspend fun getMyResumes(params: PagingParams): List<Resume> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = MainRpc.GET_MY_RESUMES,
            parameters = params.toNetworkModel()
        )
            .decodeList<SearchResumeDto>()
            .map { it.toDomainModel() }
    }

    /** Creates a resume and returns the id of the created row. */
    suspend fun createResume(draft: NewResume): String = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { error("Resume was not created") }
    ) {
        client.postgrest.rpc(
            function = MainRpc.CREATE_RESUME,
            parameters = CreateResumeParamsDto(
                title = draft.title,
                bio = draft.bio,
                githubUrl = draft.githubUrl,
                portfolioUrl = draft.portfolioUrl,
                mainSkills = draft.mainSkills,
                secondarySkills = draft.secondarySkills,
                paymentPreference = draft.paymentPreference.toNetworkValue(),
                minRate = draft.minRate,
                currency = draft.currency.toNetworkValue(),
                availability = draft.availability.map { it.toNetworkValue() },
            )
        )
            .decodeAs<String>()
    }

    suspend fun getResumeById(id: String): Resume? = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { null }
    ) {
        client.from(RESUME_TABLE)
            .select { filter { ResumeDto::id eq id } }
            .decodeList<ResumeDto>()
            .firstOrNull()
            ?.toDomainModel()
    }

    suspend fun updateResume(resume: ResumeDto) = apiRequest(
        onSuccess = {},
        onEmptyOrNull = {}
    ) {
        client.from(RESUME_TABLE)
            .update(resume) {
                filter { ResumeDto::id eq resume.id }
            }
    }
}

private const val RESUME_TABLE = "resumes"
