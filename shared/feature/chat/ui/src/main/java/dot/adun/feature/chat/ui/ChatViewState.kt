package dot.adun.feature.chat.ui

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.feature.chat.domain.entity.Message

@Immutable
data class ChatViewState(
    /** Messages ordered newest-first (rendered with a reversed layout). */
    val messages: List<Message> = emptyList(),
    val input: String = "",
    val currentUserId: String? = null,
    val hasMore: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val loadMoreState: LoadState = LoadState.NotStarted,
    val sendState: LoadState = LoadState.NotStarted,
) {
    val canSend: Boolean
        get() = input.isNotBlank()
}

sealed interface ChatScreenResult {
    data object Finish : ChatScreenResult
}
