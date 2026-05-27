package dot.adun.feature.login.ui.screen

import dot.adun.core.ui.core.BaseViewIntents

class LoginViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val register = intent("register")
    val login = intent("login")
    val changeEmail = typedIntent<String>("changeEmail")
    val changePassword = typedIntent<String>("changePassword")
}
