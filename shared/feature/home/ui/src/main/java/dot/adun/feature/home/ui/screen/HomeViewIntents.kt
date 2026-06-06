package dot.adun.feature.home.ui.screen

import dot.adun.core.ui.core.BaseViewIntents

class HomeViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val navToSearch = intent("navToSearch")
    val logout = intent("logout")
    val refresh = intent("refresh")
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
    val openProfile = intent("openProfile")
}
