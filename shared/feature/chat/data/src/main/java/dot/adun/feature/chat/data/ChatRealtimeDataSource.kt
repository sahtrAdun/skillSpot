package dot.adun.feature.chat.data

import dot.adun.feature.chat.data.dto.MessageDto
import dot.adun.feature.chat.data.mappers.toDomainModel
import dot.adun.feature.chat.domain.entity.Message
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRealtimeDataSource @Inject constructor(
    private val client: SupabaseClient,
) {
    private val cleanupScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun observeMessages(projectId: String): Flow<Message> = callbackFlow {
        val channel = client.channel("messages_${projectId}_${UUID.randomUUID()}")
        val listener = channel.postgresChangeFlow<PostgresAction.Insert>(
            schema = "public"
        ) {
            table = "messages"
            filter("project_id", FilterOperator.EQ, projectId)
        }
            .onEach { action ->
                trySend(action.decodeRecord<MessageDto>().toDomainModel())
            }
            .launchIn(this)

        channel.subscribe()

        awaitClose {
            listener.cancel()
            cleanupScope.launch { client.realtime.removeChannel(channel) }
        }
    }
}
