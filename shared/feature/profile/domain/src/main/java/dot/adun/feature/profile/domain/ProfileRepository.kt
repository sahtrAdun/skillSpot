package dot.adun.feature.profile.domain

import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.ProfileResult
import dot.adun.feature.profile.domain.entity.ProfileUpdate
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.profile.domain.entity.Review
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun fetchProfile(): ProfileResult
    suspend fun updateProfile(profile: Profile): ProfileResult
    suspend fun updateMyProfile(update: ProfileUpdate)
    suspend fun uploadAvatar(bytes: ByteArray): String
    suspend fun cacheProfile(profile: Profile)
    suspend fun readProfile(): Profile?
    suspend fun clearProfileCache()
    suspend fun logout()

    suspend fun getProfileById(id: String): PublicProfile?
    suspend fun getReviews(profileId: String, limit: Int, offset: Int): List<Review>
    suspend fun getContent(profileId: String, limit: Int, offset: Int): ProfileContent

    val profile: Flow<Profile?>
}
