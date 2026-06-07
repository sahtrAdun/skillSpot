package dot.adun.feature.search.ui

import dot.adun.core.ui.core.BaseViewIntents

class SearchViewIntents : BaseViewIntents() {
    val updateQuery = typedIntent<String>("updateQuery")
    val cancel = intent("cancel")
    val loadMore = intent("loadMore")

    /** [Pair.first] = isVacancy, [Pair.second] = id of the vacancy/resume to open. */
    val openDetails = typedIntent<Pair<Boolean, String>>("openDetails")
}
