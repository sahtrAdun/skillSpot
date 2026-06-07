package dot.adun.feature.authorized.ui.screen.applications

import dot.adun.core.ui.core.BaseViewIntents

class ApplicationsViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
    val decline = typedIntent<String>("decline")
    val approve = typedIntent<String>("approve")
}
