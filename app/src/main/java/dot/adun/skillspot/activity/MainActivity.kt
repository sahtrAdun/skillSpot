package dot.adun.skillspot.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import dagger.hilt.android.AndroidEntryPoint
import dot.adun.common.resources.PrefKeys
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.theme.ThemeType
import dot.adun.routing.nav3.AppNavigation

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionProviders {
                val theme = remember { ThemeType.from(PrefKeys.UI.THEME_SYSTEM) }

                AppTheme(
                    isDark = isDark(theme)
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
