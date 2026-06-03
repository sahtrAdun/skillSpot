package dot.adun.feature.authorized.ui.screen.active

import dot.adun.core.ui.core.BaseViewIntents

class ActiveViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
}
