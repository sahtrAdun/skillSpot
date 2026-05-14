package dot.adun.feature.home.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.secret.SecretModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.home.domain.HomeModel
import dot.adun.feature.settings.domain.SETTINGS_THEME_DARK
import dot.adun.feature.settings.domain.SettingsModel
import dot.adun.feature.settings.domain.entity.Settings
import dot.adun.feature.settings.domain.mappers.toThemeId
import dot.adun.feature.settings.domain.mappers.toThemeValue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Stable
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val model: HomeModel,
    private val secretModel: SecretModel,
    private val settingsModel: SettingsModel
) : StateViewModel<HomeViewState, HomeViewIntents, HomeScreenResult>(HomeViewState()) {
    override val intents = HomeViewIntents()
    private var currentThemeId: Int? = null

    init {
        settingsModel.themeFlow
            .map { theme -> currentThemeId = theme.toThemeId() }
            .launchIn(viewModelScope)

        onIntent(intents.navToSearch) {
            emitResult(HomeScreenResult.Search)
        }

        onIntent(intents.navToDetails) {
            val targetId = if (currentThemeId == 1) 2 else 1
            settingsModel.set(Settings.Theme, targetId)
        }
    }
}

sealed interface HomeScreenResult {
    data object Search : HomeScreenResult

    data object Finish : HomeScreenResult
}
