package dot.adun.feature.search.ui

import dot.adun.core.ui.core.BaseViewIntents

class SearchViewIntents : BaseViewIntents() {
    val updateText = typedIntent<String>("updateText")
    val validateText = intent("validateText")
    val navigateBack = intent("navigateBack")
}
