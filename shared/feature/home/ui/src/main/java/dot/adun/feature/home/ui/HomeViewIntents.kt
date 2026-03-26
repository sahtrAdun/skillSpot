package dot.adun.feature.home.ui

import dot.adun.core.ui.core.BaseViewIntents

class HomeViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val navToSearch = intent("navToSearch")
    val navToDetails = intent("navToDetails")
}
