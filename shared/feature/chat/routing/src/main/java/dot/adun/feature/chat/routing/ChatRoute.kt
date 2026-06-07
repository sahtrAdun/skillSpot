package dot.adun.feature.chat.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.chat.ui.ChatScreen
import dot.adun.feature.chat.ui.ChatViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChatRoute(
    val projectId: String,
) : Route<ChatViewModel> {
    override val id: String = "chat_route"

    @Composable
    override fun Screen(viewModel: ChatViewModel) {
        ChatScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): ChatViewModel =
        hiltViewModel<ChatViewModel, ChatViewModel.Factory> { factory: ChatViewModel.Factory ->
            factory.create(projectId)
        }
}
