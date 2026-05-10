package dot.adun.skillspot.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dot.adun.common.resources.PrefKeys
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.domain.entity.ThemeType
import dot.adun.feature.settings.domain.SettingsModel
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.domain.entity.Settings
import dot.adun.routing.nav3.AppNavigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsModel: SettingsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionProviders {
                val themeState by settingsModel.themeFlow
                    .collectAsStateWithLifecycle(PrefKeys.UI.THEME_SYSTEM)

                AppTheme(
                    isDark = isDark(ThemeType.from(themeState))
                ) {
                    AppNavigation(
                        onFinish = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
private fun isDark(theme: ThemeType): Boolean {
    return when (theme) {
        ThemeType.Dark -> true
        ThemeType.Light -> false
        ThemeType.DayNight -> TODO()
        ThemeType.System -> isSystemInDarkTheme()
    }
}

@Composable
private fun CompositionProviders(
    app: @Composable () -> Unit
) {
    CompositionLocalProvider {
        app()
    }
}
