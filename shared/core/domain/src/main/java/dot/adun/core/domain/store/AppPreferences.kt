package dot.adun.core.domain.store

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    private val preferences: PreferencesRepository
) {
    fun <T> observe(key: Preferences.Key<T>): Flow<T?> =
        preferences.observe(key)

    suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        preferences.set(key, value)
    }

    suspend fun <T> get(key: Preferences.Key<T>): T? {
        return preferences.get(key)
    }

    suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T {
        return preferences.get(key, defaultValue)
    }

    suspend fun edit(block: suspend MutablePreferences.() -> Unit) {
        preferences.edit(block)
    }

    suspend fun remove(key: Preferences.Key<*>) {
        preferences.remove(key)
    }

    suspend fun clear() {
        preferences.clear()
    }
}
