package dot.adun.feature.chat.ui

import dot.adun.core.ui.core.BaseViewIntents

class ChatViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val changeInput = typedIntent<String>("changeInput")
    val send = intent("send")
    val loadMore = intent("loadMore")
}
