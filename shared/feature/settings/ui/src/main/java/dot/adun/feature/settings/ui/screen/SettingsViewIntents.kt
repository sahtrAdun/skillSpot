package dot.adun.feature.settings.ui.screen

import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.feature.settings.domain.entity.Setting

class SettingsViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val toggleSetting = typedIntent<Setting.Toggle>("toggleSetting")
    val selectSetting = typedIntent<Setting.Selector>("selectSetting")
}
