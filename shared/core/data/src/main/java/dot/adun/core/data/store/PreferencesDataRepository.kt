package dot.adun.core.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.adun.core.domain.store.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataRepository @Inject constructor(
    @ApplicationContext context: Context
): PreferencesRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "app_preferences"
    )
    private val dataStore = context.dataStore

    override fun <T> observe(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .map { preferences -> preferences[key] }
            .catch { _ -> /* do nothing */ }
    }

    override suspend fun <T> set(key: Preferences.Key<T>, value: T): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> get(key: Preferences.Key<T>): T? = withContext(Dispatchers.IO) {
        dataStore.data
            .map { preferences -> preferences[key] }
            .firstOrNull()
    }

    override suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T = withContext(Dispatchers.IO) {
        dataStore.data
            .map { preferences -> preferences[key] ?: defaultValue }
            .first()
    }

    override suspend fun edit(block: suspend MutablePreferences.() -> Unit): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences.block()
        }
    }

    override suspend fun remove(key: Preferences.Key<*>): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
