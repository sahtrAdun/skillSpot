package dot.adun.skillspot.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dagger.hilt.android.AndroidEntryPoint
import dot.adun.core.domain.util.Constants
import dot.adun.core.ui.theme.AppTheme
import dot.adun.routing.nav3.AppNavigation

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme = remember { Constants.App.UI.SYSTEM_THEME } // todo temp

            AppTheme(isDark = isDark(theme)) {
                AppNavigation(
                    onFinish = { finish() }
                )
            }
        }
    }
}

@Composable
private fun isDark(theme: String): Boolean? {
    return when (theme) {
        Constants.App.UI.DARK_THEME -> true
        Constants.App.UI.LIGHT_THEME -> false
        else -> isSystemInDarkTheme()
    }
}
