package dot.adun.feature.authorized.ui.screen.active

import dot.adun.core.ui.core.BaseViewIntents

class ActiveViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
    val openChat = typedIntent<String>("openChat")
    val complete = typedIntent<String>("complete")
    val submitReview = typedIntent<ReviewArgs>("submitReview")
    val dismissReview = intent("dismissReview")
}
