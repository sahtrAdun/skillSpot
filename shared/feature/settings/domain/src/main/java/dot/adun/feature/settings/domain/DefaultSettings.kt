package dot.adun.feature.settings.domain

import dot.adun.common.resources.PrefKeys
import dot.adun.core.domain.entity.strRef
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.domain.entity.Settings

val defaultSettings = listOf<Setting>(
    Setting.Selector(
        id = Setting.Id(Settings.Theme.id),
        value = SETTINGS_THEME_SYSTEM,
        options = listOf(
            Setting.Option(
                id = SETTINGS_THEME_SYSTEM,
                value = PrefKeys.UI.THEME_SYSTEM,
                label = strRef("")
            ),
            Setting.Option(
                id = SETTINGS_THEME_LIGHT,
                value = PrefKeys.UI.THEME_LIGHT,
                label = strRef("")
            ),
            Setting.Option(
                id = SETTINGS_THEME_DARK,
                value = PrefKeys.UI.THEME_DARK,
                label = strRef("")
            ),
            Setting.Option(
                id = SETTINGS_THEME_DAY_NIGHT,
                value = PrefKeys.UI.THEME_DAY_NIGHT,
                label = strRef("")
            ),
        ),
        title = strRef(""),
        description = null
    )
)

const val SETTINGS_THEME_SYSTEM = 0
const val SETTINGS_THEME_LIGHT = 1
const val SETTINGS_THEME_DARK = 2
const val SETTINGS_THEME_DAY_NIGHT = 3
