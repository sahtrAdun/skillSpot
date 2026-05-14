package dot.adun.feature.settings.domain

import dot.adun.core.domain.entity.Theme
import dot.adun.core.domain.util.mapUntilChanged
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.domain.entity.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsModel @Inject constructor(
    private val repository: SettingsRepository,
) {
    val settings: Flow<List<Setting>> = repository.localSettings

    val themeFlow: Flow<Theme> = repository
        .observe(Setting.Id(Settings.Theme.id))
        .mapUntilChanged {
            val id = (it as? Setting.Selector)?.selectedOption?.value
            Theme.fromId(id)
        }

    fun flow(setting: Settings): Flow<Setting?> = repository
        .observe(Setting.Id(setting.id))

    fun find(setting: Settings): Setting? {
        return repository.get(Setting.Id(setting.id))
    }

    suspend fun set(setting: Settings, value: Any) = repository.set(Setting.Id(setting.id), value)
}
