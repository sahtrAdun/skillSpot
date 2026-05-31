package dot.adun.feature.settings.ui.screen

import androidx.compose.runtime.Immutable

@Immutable
data class SettingsViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
)
