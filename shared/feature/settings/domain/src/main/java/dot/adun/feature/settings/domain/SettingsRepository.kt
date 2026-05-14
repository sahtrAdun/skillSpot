package dot.adun.feature.settings.domain

import dot.adun.feature.settings.domain.entity.Setting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val localSettings: Flow<List<Setting>>

    suspend fun set(id: Setting.Id, value: Any)
    fun get(id: Setting.Id): Setting?
    fun observe(id: Setting.Id): Flow<Setting?>
}
