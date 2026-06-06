package dot.adun.feature.authorized.ui.screen.details.resume

import dot.adun.core.ui.core.BaseViewIntents

class ResumeDetailsViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val edit = intent("edit")
    val openProfile = typedIntent<String>("openProfile")
}
