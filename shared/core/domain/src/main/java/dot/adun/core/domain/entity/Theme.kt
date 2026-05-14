package dot.adun.core.domain.entity

import dot.adun.common.resources.PrefKeys
import javax.annotation.concurrent.Immutable

@Immutable
enum class Theme(
    val code: Int,
    val id: String
) {
    System(0, SYSTEM_KEY),
    Light(1, LIGHT_KEY),
    Dark(2, DARK_KEY),
    DayNight(3, DAY_NIGHT_KEY);

    companion object {
        fun fromCode(code: Int) = entries.firstOrNull { it.code == code } ?: System
        fun fromId(id: String?) = entries.firstOrNull { it.id == id } ?: System
    }
}

private const val SYSTEM_KEY = PrefKeys.UI.THEME_SYSTEM
private const val DARK_KEY = PrefKeys.UI.THEME_DARK
private const val LIGHT_KEY = PrefKeys.UI.THEME_LIGHT
private const val DAY_NIGHT_KEY = PrefKeys.UI.THEME_DAY_NIGHT
