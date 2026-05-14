package dot.adun.core.domain.store

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun <T> observe(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T> set(key: Preferences.Key<T>, value: T)
    suspend fun <T> get(key: Preferences.Key<T>): T?
    suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun edit(block: suspend MutablePreferences.() -> Unit)
    suspend fun remove(key: Preferences.Key<*>)
    suspend fun clear()
}
