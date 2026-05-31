package dot.adun.skillspot.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dot.adun.core.domain.DayNightState
import dot.adun.core.domain.calculateNextUpdateTime
import dot.adun.core.domain.entity.Theme
import dot.adun.core.ui.LocalUserRole
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.profile.domain.ProfileModel
import dot.adun.feature.settings.domain.SettingsModel
import dot.adun.routing.nav3.AppNavigation
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsModel: SettingsModel
    @Inject
    lateinit var profileModel: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profile by profileModel.profile.collectAsStateWithLifecycle(null)
            val userRole = profile?.role ?: UserRole.None

            CompositionProviders(
                userRole = userRole
            ) {
                val themeState by settingsModel.themeFlow.collectAsStateWithLifecycle(Theme.System)

                AppTheme(
                    isDark = isDark(themeState)
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
private fun isDark(theme: Theme) = when (theme) {
    Theme.Light -> false
    Theme.Dark -> true
    Theme.DayNight -> {
        /*val state by dayNightThemeController()
        state.isDark*/
        isSystemInDarkTheme()
    }
    Theme.System -> isSystemInDarkTheme()
}

@Composable
private fun dayNightThemeController(): State<DayNightState> {
    return produceState(initialValue = DayNightState.getCurrent()) {
        while (true) {
            val now = DayNightState.getCurrent()
            if (value != now) {
                value = now
            }

            val nextUpdate = calculateNextUpdateTime()
            delay(nextUpdate)
        }
    }
}


@Composable
private fun CompositionProviders(
    userRole: UserRole,
    app: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalUserRole provides userRole
    ) {
        app()
    }
}
