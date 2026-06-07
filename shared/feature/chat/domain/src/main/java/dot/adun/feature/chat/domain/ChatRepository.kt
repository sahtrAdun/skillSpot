package dot.adun.feature.chat.domain

import dot.adun.feature.chat.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getMessages(projectId: String, limit: Int, offset: Int): List<Message>
    suspend fun sendMessage(projectId: String, content: String): String
    fun observeMessages(projectId: String): Flow<Message>
}
