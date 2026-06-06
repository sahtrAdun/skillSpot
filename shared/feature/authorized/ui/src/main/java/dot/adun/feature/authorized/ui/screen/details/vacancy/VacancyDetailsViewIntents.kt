package dot.adun.feature.authorized.ui.screen.details.vacancy

import dot.adun.core.ui.core.BaseViewIntents

class VacancyDetailsViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val edit = intent("edit")
    val openProfile = typedIntent<String>("openProfile")
    val openApplySheet = intent("openApplySheet")
    val dismissApplySheet = intent("dismissApplySheet")
    val apply = typedIntent<ApplyArgs>("apply")
}
