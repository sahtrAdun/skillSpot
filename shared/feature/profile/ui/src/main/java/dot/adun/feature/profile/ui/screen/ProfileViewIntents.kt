package dot.adun.feature.profile.ui.screen

import dot.adun.core.ui.core.BaseViewIntents

class ProfileViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val selectTab = typedIntent<ProfileTab>("selectTab")

    /** [Pair.first] = isVacancy, [Pair.second] = content id. */
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
    val openProfile = typedIntent<String>("openProfile")
    val logout = intent("logout")
}
