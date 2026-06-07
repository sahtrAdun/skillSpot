package dot.adun.feature.authorized.data

import dot.adun.feature.authorized.data.dto.ProjectDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRealtimeDataSource @Inject constructor(
    private val client: SupabaseClient,
) {
    fun observeCompletedProjects(freelancerId: String): Flow<String> = flow {
        val channel = client.channel("projects_$freelancerId")

        val updates = channel.postgresChangeFlow<PostgresAction.Update>(
            schema = "public"
        ) {
            table = "projects"
            filter("freelancer_id", FilterOperator.EQ, freelancerId)
        }
            .mapNotNull { action ->
                val project = action.decodeRecord<ProjectDto>()
                project.id.takeIf { project.status.equals("completed", ignoreCase = true) }
            }

        channel.subscribe()
        emitAll(updates)
    }
}
