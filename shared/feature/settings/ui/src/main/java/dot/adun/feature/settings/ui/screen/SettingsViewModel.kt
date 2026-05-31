package dot.adun.feature.settings.ui.screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.settings.domain.SettingsModel
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.domain.entity.Settings
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsModel: SettingsModel,
) : StateViewModel<SettingsViewState, SettingsViewIntents, SettingsScreenResult>(SettingsViewState()) {
    override val intents = SettingsViewIntents()

    init {
        on(settingsModel.settings) { settings ->
            update { state -> state.copy(settings = settings) }
        }

        onIntent(intents.navigateBack) {
            emitResult(SettingsScreenResult.Finish)
        }

        onIntent(intents.toggleSetting) { setting ->
            action {
                val toggled = setting.copy(value = !setting.value)
                val entity = findSettings(setting.id) ?: return@action
                settingsModel.set(entity, toggled.value)
            }
        }

        onIntent(intents.selectSetting) { setting ->
            action {
                val entity = findSettings(setting.id) ?: return@action
                emitEvent(
                    settingsSelectorDialog(
                        title = setting.title,
                        options = setting.options,
                        selectedId = setting.value,
                        onSelect = { optionId ->
                            viewModelScope.launch {
                                settingsModel.set(entity, optionId)
                            }
                        }
                    )
                )
            }
        }
    }

    private fun findSettings(id: Setting.Id): Settings? {
        if (id.value == Settings.Theme.id) return Settings.Theme
        return null
    }
}

sealed interface SettingsScreenResult {
    data object Finish : SettingsScreenResult
}
