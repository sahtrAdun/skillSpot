package dot.adun.feature.authorized.ui.screen.active

import dot.adun.core.ui.core.BaseViewIntents

class ActiveViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")

    /** Opens a chat for the given vacancy/project id. Behaviour defined later. */
    val openChat = typedIntent<String>("openChat")

    /** Client completes the project with the given id. */
    val complete = typedIntent<String>("complete")

    val submitReview = typedIntent<ReviewArgs>("submitReview")
    val dismissReview = intent("dismissReview")
}
