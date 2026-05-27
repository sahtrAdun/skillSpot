package dot.adun.core.domain

import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.error.AppError
import dot.adun.core.domain.mappers.toAppError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class TaskJob<VS, T> constructor(
    private val scope: CoroutineScope,
    private val currentState: () -> VS,
    private val onStateUpdate: ((VS) -> VS) -> Unit,
    private val stateRead: (VS) -> LoadState,
    private val stateWrite: (VS, LoadState) -> VS
) {
    private var actionBlock: (suspend (VS) -> T)? = null
    private var successBlock: (suspend (T) -> Unit)? = null
    private var errorBlock: (suspend (AppError) -> Unit)? = null
    private var successReducer: ((VS, T) -> VS)? = null

    fun job(block: suspend (VS) -> T) {
        this.actionBlock = block
    }

    fun onSuccess(
        reducer: ((VS, T) -> VS)? = null,
        action: (suspend (T) -> Unit)? = null
    ) {
        this.successReducer = reducer
        this.successBlock = action
    }

    fun onError(action: (suspend (AppError) -> Unit)? = null) {
        this.errorBlock = action
    }

    fun start() {
        val currentStatus = stateRead(currentState())
        if (currentStatus is LoadState.Loading) {
            Napier.w(tag = "TaskJob") { "Task is already running. Execution skipped." }
            return
        }
        val work = actionBlock ?: throw IllegalStateException("doWork {...} must be defined")

        scope.launch {
            try {
                onStateUpdate { stateWrite(it, LoadState.Loading) }
                val result = work(currentState())
                onStateUpdate { pcState ->
                    val stateWithData = successReducer?.invoke(pcState, result) ?: pcState
                    stateWrite(stateWithData, LoadState.Done)
                }
                successBlock?.invoke(result)
            } catch (ce: CancellationException) {
                throw ce
            } catch (e: Exception) {
                val appError = e.toAppError()
                onStateUpdate { stateWrite(it, LoadState.Error(appError)) }
                errorBlock?.invoke(appError)
            }
        }
    }
}
