package dot.adun.feature.profile.domain

import androidx.datastore.preferences.core.longPreferencesKey
import dot.adun.core.domain.store.AppPreferences
import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.ProfileUpdate
import kotlinx.coroutines.flow.lastOrNull
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor(
    private val repository: ProfileRepository,
    private val preferences: AppPreferences
) {
    val profile = repository.profile
    val lastProfileUpdateTime = preferences
        .observe(longPreferencesKey(LAST_PROFILE_UPDATE_TIME_KEY))

    suspend fun readProfile(): Profile? {
        return repository.readProfile()
    }

    suspend fun fetchProfile() {
        val lastUpdateTime = lastProfileUpdateTime.lastOrNull()
        if (lastUpdateTime != null && (lastUpdateTime + updateInterval) > currentTime) {
            return
        }

        repository.fetchProfile()
        preferences.set(longPreferencesKey(LAST_PROFILE_UPDATE_TIME_KEY), currentTime)
    }

    suspend fun logout() {
        repository.logout()
        preferences.remove(longPreferencesKey(LAST_PROFILE_UPDATE_TIME_KEY))
    }

    suspend fun uploadAvatar(bytes: ByteArray): String =
        repository.uploadAvatar(bytes)

    /**
     * Updates the current user's profile via RPC and keeps the locally cached
     * [Profile] in sync so observers (home, profile screens) reflect the change.
     */
    suspend fun updateMyProfile(update: ProfileUpdate) {
        repository.updateMyProfile(update)

        val current = repository.readProfile() ?: return
        repository.cacheProfile(
            current.copy(
                personalInfo = current.personalInfo.copy(
                    fullName = update.fullName,
                    bio = update.bio,
                    age = update.age,
                    country = update.country,
                    city = update.city,
                ),
                avatarUrl = update.avatarUrl,
            )
        )
    }

    suspend fun getProfileById(id: String) =
        repository.getProfileById(id)

    suspend fun getReviews(profileId: String, limit: Int = DEFAULT_PAGE_SIZE, offset: Int = 0) =
        repository.getReviews(profileId, limit, offset)

    suspend fun getContent(profileId: String, limit: Int = DEFAULT_PAGE_SIZE, offset: Int = 0) =
        repository.getContent(profileId, limit, offset)
}

private const val DEFAULT_PAGE_SIZE = 20

private val updateInterval = Duration.ofMinutes(5).toMillis()
private val currentTime: Long get() = System.currentTimeMillis()
private const val LAST_PROFILE_UPDATE_TIME_KEY = "last_profile_update_time"
