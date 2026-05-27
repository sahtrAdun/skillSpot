package dot.adun.feature.register.ui.screen

import dot.adun.core.ui.core.BaseViewIntents

class RegisterViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val register = intent("register")
    val login = intent("login")
    val changeEmail = typedIntent<String>("changeEmail")
    val changePassword = typedIntent<String>("changePassword")
    val changeSecondPassword = typedIntent<String>("changeSecondPassword")
}