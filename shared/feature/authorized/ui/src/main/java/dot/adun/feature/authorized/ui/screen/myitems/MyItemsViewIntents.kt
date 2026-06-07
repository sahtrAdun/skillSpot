package dot.adun.feature.authorized.ui.screen.myitems

import dot.adun.core.ui.core.BaseViewIntents

class MyItemsViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val loadMore = intent("loadMore")

    /** [Pair.first] = isVacancy, [Pair.second] = id of the vacancy/resume to open. */
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
}
