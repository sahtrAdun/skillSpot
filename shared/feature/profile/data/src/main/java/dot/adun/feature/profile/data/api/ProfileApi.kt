package dot.adun.feature.profile.data.api

import dot.adun.core.data.apiRequest
import dot.adun.core.data.selectFilter
import dot.adun.feature.profile.data.dto.GetContentParamsDto
import dot.adun.feature.profile.data.dto.GetReviewsParamsDto
import dot.adun.feature.profile.data.dto.GetUserProfileParamsDto
import dot.adun.feature.profile.data.dto.ProfileContentWrapperDto
import dot.adun.feature.profile.data.dto.ProfileDto
import dot.adun.feature.profile.data.dto.PublicProfileDto
import dot.adun.feature.profile.data.dto.ReviewWithSenderDto
import dot.adun.feature.profile.data.mappers.toDomainModel
import dot.adun.feature.profile.data.mappers.toProfileContent
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.ProfileResult
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.profile.domain.entity.Review
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileApi @Inject constructor(
    private val client: SupabaseClient
) {
    suspend fun fetchProfile(): ProfileResult = apiRequest(
        onEmptyOrNull = { ProfileResult.UserNotFound },
        onSuccess = { profile -> ProfileResult.SuccessFetch(profile.toDomainModel()) }
    ) {
        val id = authUser(true).id
        client.from(PROFILE_TABLE)
            .selectFilter { ProfileDto::userId eq id }
            .decodeList<ProfileDto>()
            .firstOrNull()
    }

    suspend fun updateProfile(profile: ProfileDto): ProfileResult = apiRequest(
        onEmptyOrNull = { ProfileResult.UpdateFailed },
        onSuccess = { ProfileResult.UpdateSuccess }
    ) {
        try {
            client.from(PROFILE_TABLE).update(profile) {
                filter { ProfileDto::userId eq profile.userId }
            }
        } catch (_: Exception) {
            ProfileResult.UpdateFailed
        }
    }

    suspend fun getProfileById(id: String): PublicProfile? = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { null }
    ) {
        client.postgrest.rpc(
            function = Functions.GET_USER_PROFILE_BY_ID,
            parameters = GetUserProfileParamsDto(profileId = id)
        )
            .decodeList<PublicProfileDto>()
            .firstOrNull()
            ?.toDomainModel()
    }

    suspend fun getReviews(profileId: String, limit: Int, offset: Int): List<Review> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = Functions.GET_REVIEWS_BY_PROFILE_ID,
            parameters = GetReviewsParamsDto(
                profileId = profileId,
                limit = limit,
                offset = offset,
            )
        )
            .decodeList<ReviewWithSenderDto>()
            .map { it.toDomainModel() }
    }

    suspend fun getContent(profileId: String, limit: Int, offset: Int): ProfileContent = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { ProfileContent.Empty }
    ) {
        client.postgrest.rpc(
            function = Functions.GET_USER_CONTENT_BY_PROFILE_ID,
            parameters = GetContentParamsDto(
                profileId = profileId,
                limit = limit,
                offset = offset,
            )
        )
            .decodeList<ProfileContentWrapperDto>()
            .toProfileContent()
    }

    private suspend fun authUser(updateSession: Boolean = false) = client.auth
        .retrieveUserForCurrentSession(updateSession = updateSession)
}

private object Functions {
    const val GET_USER_PROFILE_BY_ID = "get_user_profile_by_id"
    const val GET_REVIEWS_BY_PROFILE_ID = "get_reviews_by_profile_id"
    const val GET_USER_CONTENT_BY_PROFILE_ID = "get_user_content_by_profile_id"
}

private const val PROFILE_TABLE = "profiles"
