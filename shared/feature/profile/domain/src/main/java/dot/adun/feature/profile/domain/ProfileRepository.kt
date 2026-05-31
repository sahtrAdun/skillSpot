package dot.adun.feature.profile.domain

import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.ProfileResult
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun fetchProfile(): ProfileResult
    suspend fun updateProfile(profile: Profile): ProfileResult
    suspend fun cacheProfile(profile: Profile)
    suspend fun readProfile(): Profile?
    suspend fun clearProfileCache()
    suspend fun logout()

    val profile: Flow<Profile?>
}
