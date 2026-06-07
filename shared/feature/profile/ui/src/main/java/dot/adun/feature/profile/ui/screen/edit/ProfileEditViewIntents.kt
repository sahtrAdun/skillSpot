package dot.adun.feature.profile.ui.screen.edit

import dot.adun.core.ui.core.BaseViewIntents

class ProfileEditViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val changeFullName = typedIntent<String>("changeFullName")
    val changeBio = typedIntent<String>("changeBio")
    val changeAge = typedIntent<String>("changeAge")
    val changeCountry = typedIntent<String>("changeCountry")
    val changeCity = typedIntent<String>("changeCity")
    val pickAvatar = typedIntent<ByteArray>("pickAvatar")
    val save = intent("save")
}
