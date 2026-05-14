package dot.adun.core.domain.entity

import dot.adun.common.resources.PrefKeys
import javax.annotation.concurrent.Immutable

@Immutable
enum class ThemeType(val theme: String) {
    Dark(PrefKeys.UI.THEME_DARK),
    Light(PrefKeys.UI.THEME_LIGHT),
    DayNight(PrefKeys.UI.THEME_DAY_NIGHT),
    System(PrefKeys.UI.THEME_SYSTEM);

    companion object {
        fun from(theme: String): ThemeType = entries
            .find { it.theme == theme }
            ?: System
    }
}
