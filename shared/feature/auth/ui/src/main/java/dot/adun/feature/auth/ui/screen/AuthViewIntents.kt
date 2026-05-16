package dot.adun.feature.auth.ui.screen

import dot.adun.core.ui.core.BaseViewIntents

class AuthViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val login = intent("login")
    val register = intent("register")
}