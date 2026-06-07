package dot.adun.feature.profile.data

import androidx.datastore.preferences.core.stringPreferencesKey
import dot.adun.core.domain.store.AppPreferences
import dot.adun.core.domain.util.mapUntilChanged
import dot.adun.feature.profile.data.api.ProfileApi
import dot.adun.feature.profile.data.dto.ProfileDto
import dot.adun.feature.profile.data.mappers.toDomainModel
import dot.adun.feature.profile.data.mappers.toNetworkModel
import dot.adun.feature.profile.domain.ProfileRepository
import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.ProfileResult
import dot.adun.feature.profile.domain.entity.ProfileUpdate
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.profile.domain.entity.Review
import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileDataRepository @Inject constructor(
    private val profileApi: ProfileApi,
    private val appPreferences: AppPreferences,
    private val client: SupabaseClient
) : ProfileRepository {
    override val profile = appPreferences.observe(stringPreferencesKey(PROFILE_KEY))
        .mapUntilChanged { encodedProfile ->
            encodedProfile  ?: return@mapUntilChanged null

            Json.decodeFromString(
                deserializer = ProfileDto.serializer(),
                string = encodedProfile
            )
                .toDomainModel()
        }

    override suspend fun fetchProfile(): ProfileResult {
        clearProfileCache()
        return profileApi.fetchProfile()
    }

    override suspend fun updateProfile(profile: Profile): ProfileResult {
        return profileApi.updateProfile(profile.toNetworkModel())
    }

    override suspend fun updateMyProfile(update: ProfileUpdate) {
        profileApi.updateMyProfile(update)
    }

    override suspend fun uploadAvatar(bytes: ByteArray): String {
        return profileApi.uploadAvatar(bytes)
    }

    override suspend fun cacheProfile(profile: Profile) {
        try {
            clearProfileCache()
            val encodedProfile = Json.encodeToString(
                serializer = ProfileDto.serializer(),
                value = profile.toNetworkModel()
            )

            appPreferences.set(
                key = stringPreferencesKey(PROFILE_KEY),
                value = encodedProfile
            )
        } catch (e: Exception) {
            Napier.w(e) { e.localizedMessage ?: e.message ?: "Failed to cache profile" }
        }
    }

    override suspend fun readProfile(): Profile? {
        return try {
            val encodedProfile = appPreferences.get(stringPreferencesKey(PROFILE_KEY))
                ?: return null

            Json.decodeFromString(
                deserializer = ProfileDto.serializer(),
                string = encodedProfile
            )
                .toDomainModel()
        } catch (e: Exception) {
            Napier.w(e) { e.localizedMessage ?: e.message ?: "Failed to read profile" }
            null
        }
    }

    override suspend fun getProfileById(id: String): PublicProfile? {
        return profileApi.getProfileById(id)
    }

    override suspend fun getReviews(profileId: String, limit: Int, offset: Int): List<Review> {
        return profileApi.getReviews(profileId, limit, offset)
    }

    override suspend fun getContent(profileId: String, limit: Int, offset: Int): ProfileContent {
        return profileApi.getContent(profileId, limit, offset)
    }

    override suspend fun clearProfileCache() {
        appPreferences.remove(stringPreferencesKey(PROFILE_KEY))
    }

    override suspend fun logout() {
        try {
            client.auth.signOut()
            client.auth.clearSession()
            clearProfileCache()
        } catch (e: Exception) {
            Napier.w(e) { e.localizedMessage ?: e.message ?: "Failed to logout" }
            error("Failed to logout")
        }
    }
}

private const val PROFILE_KEY = "stored_user_profile"
