package dot.adun.feature.settings.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import dot.adun.core.domain.di.ApplicationScope
import dot.adun.core.domain.store.AppPreferences
import dot.adun.core.domain.util.mapUntilChanged
import dot.adun.feature.settings.domain.SettingsRepository
import dot.adun.feature.settings.domain.defaultSettings
import dot.adun.feature.settings.domain.entity.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataRepository @Inject constructor(
    private val appPreferences: AppPreferences,
    @ApplicationScope private val scope: CoroutineScope,
) : SettingsRepository {
    private data class State(
        val settingsMap: Map<Setting.Id, Setting>
    )

    private val _state = MutableStateFlow(State(emptyMap()))

    override val localSettings: Flow<List<Setting>> =
        _state.mapUntilChanged { it.settingsMap.values.toList() }

    override suspend fun set(id: Setting.Id, value: Any) {
        println(_state.value.settingsMap)
        val current = _state.value.settingsMap[id] ?: return

        when (current) {
            is Setting.Toggle -> {
                val newValue = value as? Boolean ?: return
                appPreferences.set(current.key(), newValue)
                updateState(current.copy(value = newValue))
            }
            is Setting.Selector -> {
                val newValue = value as? Int ?: return
                appPreferences.set(current.key(), newValue)
                updateState(current.copy(value = newValue))
            }
        }
    }

    override fun get(id: Setting.Id): Setting? =
        _state.value.settingsMap[id]

    override fun observe(id: Setting.Id): Flow<Setting?> =
        _state.mapUntilChanged { it.settingsMap[id] }

    init {
        scope.launch {
            restoreSettings()
        }
    }

    private fun updateState(updated: Setting) {
        _state.update { state ->
            state.copy(
                settingsMap = state.settingsMap + (updated.id to updated)
            )
        }
    }

    private suspend fun restoreSettings() {
        val restored = defaultSettings.associate { setting ->
            val resolved = when (setting) {
                is Setting.Toggle -> {
                    val key = setting.key()
                    val stored = appPreferences.get(key)
                    if (stored == null) appPreferences.set(key, setting.value)
                    setting.copy(value = stored ?: setting.value)
                }
                is Setting.Selector -> {
                    val key = setting.key()
                    val stored = appPreferences.get(key)
                    if (stored == null) appPreferences.set(key, setting.value)
                    setting.copy(value = stored ?: setting.value)
                }
            }

            setting.id to resolved
        }
        _state.value = State(restored)
    }
}
