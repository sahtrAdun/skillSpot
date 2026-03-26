package dot.adun.feature.search.ui

import dot.adun.core.ui.core.BaseViewIntents

class SearchViewIntents : BaseViewIntents() {
    val updateText = typedIntent<String>()
    val validateText = intent()
    val navigateBack = intent()
}
