package dot.adun.feature.settings.ui.screen

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import javax.inject.Inject

@Stable
@HiltViewModel
class SettingsViewModel @Inject constructor(
) : StateViewModel<SettingsViewState, SettingsViewIntents, SettingsScreenResult>(SettingsViewState()) {
    override val intents = SettingsViewIntents()
}

sealed interface SettingsScreenResult {
    data object Finish : SettingsScreenResult
}
