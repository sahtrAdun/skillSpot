package dot.adun.feature.profile.domain

import androidx.datastore.preferences.core.longPreferencesKey
import dot.adun.core.domain.store.AppPreferences
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
}

private val updateInterval = Duration.ofMinutes(5).toMillis()
private val currentTime: Long get() = System.currentTimeMillis()
private const val LAST_PROFILE_UPDATE_TIME_KEY = "last_profile_update_time"
