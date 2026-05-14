package dot.adun.feature.settings.domain.mappers

import dot.adun.common.resources.PrefKeys
import dot.adun.feature.settings.domain.SETTINGS_THEME_DARK
import dot.adun.feature.settings.domain.SETTINGS_THEME_DAY_NIGHT
import dot.adun.feature.settings.domain.SETTINGS_THEME_LIGHT
import dot.adun.feature.settings.domain.SETTINGS_THEME_SYSTEM

fun String.toThemeId(): Int {
    return when (this) {
        PrefKeys.UI.THEME_LIGHT -> SETTINGS_THEME_LIGHT
        PrefKeys.UI.THEME_DARK -> SETTINGS_THEME_DARK
        PrefKeys.UI.THEME_DAY_NIGHT -> SETTINGS_THEME_DAY_NIGHT
        else -> SETTINGS_THEME_SYSTEM
    }
}

fun Int.toThemeValue(): String {
    return when (this) {
        SETTINGS_THEME_LIGHT -> PrefKeys.UI.THEME_LIGHT
        SETTINGS_THEME_DARK -> PrefKeys.UI.THEME_DARK
        SETTINGS_THEME_DAY_NIGHT -> PrefKeys.UI.THEME_DAY_NIGHT
        else -> PrefKeys.UI.THEME_SYSTEM
    }
}
