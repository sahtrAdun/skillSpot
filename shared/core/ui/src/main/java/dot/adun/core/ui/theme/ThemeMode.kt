package dot.adun.core.ui.theme

import androidx.compose.runtime.Immutable
import dot.adun.common.resources.PrefKeys

@Immutable
enum class ThemeType {
    Dark, Light, DayNight, System;

    companion object {
        fun from(theme: String): ThemeType = when (theme) {
            PrefKeys.UI.THEME_LIGHT -> Light
            PrefKeys.UI.THEME_DARK -> Dark
            PrefKeys.UI.THEME_DAY_NIGHT -> DayNight
            else -> System
        }
    }
}
