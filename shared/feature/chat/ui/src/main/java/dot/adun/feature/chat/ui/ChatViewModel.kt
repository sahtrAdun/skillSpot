package dot.adun.feature.chat.ui

import androidx.compose.runtime.Stable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.chat.domain.ChatModel
import dot.adun.feature.chat.domain.entity.Message
import dot.adun.feature.profile.domain.ProfileModel

@Stable
@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted private val projectId: String,
    private val model: ChatModel,
    private val profileModel: ProfileModel,
) : StateViewModel<ChatViewState, ChatViewIntents, ChatScreenResult>(ChatViewState()) {
    @AssistedFactory
    interface Factory {
        fun create(projectId: String): ChatViewModel
    }

    override val intents = ChatViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(ChatScreenResult.Finish)
        }

        onIntent(intents.changeInput) { input ->
            update { state -> state.copy(input = input) }
        }

        onIntent(intents.send) {
            send()
        }

        onIntent(intents.loadMore) {
            loadMore()
        }

        on(profileModel.profile) { profile ->
            update { state -> state.copy(currentUserId = profile?.id) }
        }

        on(model.observeMessages(projectId)) { message ->
            update { state ->
                state.copy(messages = state.messages.mergedWith(listOf(message)))
            }
        }

        loadInitial()
    }

    private fun loadInitial() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getMessages(projectId, offset = 0) }
            onSuccess { page ->
                update { state ->
                    state.copy(
                        messages = state.messages.mergedWith(page),
                        hasMore = page.size == ChatModel.PAGE_SIZE,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun loadMore() {
        val current = state.value
        if (!current.hasMore || current.loadMoreState == dot.adun.core.domain.entity.LoadState.Loading) {
            return
        }

        task(
            stateRead = { vs -> vs.loadMoreState },
            stateWrite = { vs, ls -> vs.copy(loadMoreState = ls) }
        ) {
            job { model.getMessages(projectId, offset = state.value.messages.size) }
            onSuccess { older ->
                update { state ->
                    state.copy(
                        messages = state.messages.mergedWith(older),
                        hasMore = older.size == ChatModel.PAGE_SIZE,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun send() {
        val content = state.value.input.trim()
        if (content.isBlank()) return

        task(
            stateRead = { vs -> vs.sendState },
            stateWrite = { vs, ls -> vs.copy(sendState = ls) }
        ) {
            job { model.sendMessage(projectId, content) }
            onSuccess {
                update { state -> state.copy(input = "") }
            }
            onError { errorSnack(it) }
        }
    }
}

private fun List<Message>.mergedWith(other: List<Message>): List<Message> =
    (this + other)
        .distinctBy { it.id }
        .sortedByDescending { it.createdAt }
