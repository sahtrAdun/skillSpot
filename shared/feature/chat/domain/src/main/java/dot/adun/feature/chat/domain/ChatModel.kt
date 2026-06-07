package dot.adun.feature.chat.domain

import javax.inject.Inject

class ChatModel @Inject constructor(
    private val repository: ChatRepository,
) {
    companion object {
        const val PAGE_SIZE = 30
    }

    suspend fun getMessages(projectId: String, limit: Int = PAGE_SIZE, offset: Int = 0) =
        repository.getMessages(projectId, limit, offset)

    suspend fun sendMessage(projectId: String, content: String) =
        repository.sendMessage(projectId, content)

    fun observeMessages(projectId: String) =
        repository.observeMessages(projectId)
}
