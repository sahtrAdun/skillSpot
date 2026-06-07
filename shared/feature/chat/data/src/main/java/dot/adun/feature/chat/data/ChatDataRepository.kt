package dot.adun.feature.chat.data

import dot.adun.feature.chat.data.api.ChatApi
import dot.adun.feature.chat.domain.ChatRepository
import dot.adun.feature.chat.domain.entity.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatDataRepository @Inject constructor(
    private val chatApi: ChatApi,
    private val realtime: ChatRealtimeDataSource,
) : ChatRepository {
    override suspend fun getMessages(projectId: String, limit: Int, offset: Int): List<Message> {
        return chatApi.getMessages(projectId, limit, offset)
    }

    override suspend fun sendMessage(projectId: String, content: String): String {
        return chatApi.sendMessage(projectId, content)
    }

    override fun observeMessages(projectId: String): Flow<Message> {
        return realtime.observeMessages(projectId)
    }
}
