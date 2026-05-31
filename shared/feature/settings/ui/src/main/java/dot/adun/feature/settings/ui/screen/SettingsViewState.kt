package dot.adun.feature.settings.ui.screen

import androidx.compose.runtime.Immutable
import dot.adun.feature.settings.domain.entity.Setting

@Immutable
data class SettingsViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val settings: List<Setting> = emptyList(),
)
