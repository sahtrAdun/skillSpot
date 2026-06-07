package dot.adun.feature.chat.data.api

import dot.adun.core.data.apiRequest
import dot.adun.feature.chat.data.dto.GetMessagesParamsDto
import dot.adun.feature.chat.data.dto.MessageDto
import dot.adun.feature.chat.data.dto.SendMessageParamsDto
import dot.adun.feature.chat.data.mappers.toDomainModel
import dot.adun.feature.chat.domain.entity.Message
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatApi @Inject constructor(
    private val client: SupabaseClient,
) {
    suspend fun getMessages(projectId: String, limit: Int, offset: Int): List<Message> = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { emptyList() }
    ) {
        client.postgrest.rpc(
            function = Functions.GET_PROJECT_MESSAGES,
            parameters = GetMessagesParamsDto(
                projectId = projectId,
                limit = limit,
                offset = offset,
            )
        )
            .decodeList<MessageDto>()
            .map { it.toDomainModel() }
    }

    suspend fun sendMessage(projectId: String, content: String): String = apiRequest(
        onSuccess = { it },
        onEmptyOrNull = { error("Message was not sent") }
    ) {
        client.postgrest.rpc(
            function = Functions.SEND_PROJECT_MESSAGE,
            parameters = SendMessageParamsDto(
                projectId = projectId,
                content = content,
            )
        )
            .decodeAs<String>()
    }
}

private object Functions {
    const val GET_PROJECT_MESSAGES = "get_project_messages"
    const val SEND_PROJECT_MESSAGE = "send_project_message"
}
