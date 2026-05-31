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
    private var successStateWrite: ((VS, T) -> VS)? = null
    private var errorStateWrite: ((VS, AppError) -> VS)? = null

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

    fun onSuccessStateWrite(reducer: (VS, T) -> VS) {
        this.successStateWrite = reducer
    }

    fun onErrorStateWrite(reducer: (VS, AppError) -> VS) {
        this.errorStateWrite = reducer
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
                    var newState = pcState
                    newState = successReducer?.invoke(newState, result) ?: newState
                    newState = successStateWrite?.invoke(newState, result) ?: newState
                    stateWrite(newState, LoadState.Done)
                }
                successBlock?.invoke(result)
            } catch (ce: CancellationException) {
                Napier.w(tag = "TaskJob") { "Job cancelled" }
                throw ce
            } catch (e: Exception) {
                Napier.w(e) { e.localizedMessage ?: e.message ?: "Job failed" }
                val appError = e.toAppError()
                onStateUpdate { pcState ->
                    val newState = errorStateWrite?.invoke(pcState, appError) ?: pcState
                    stateWrite(newState, LoadState.Error(appError))
                }
                errorBlock?.invoke(appError)
            }
        }
    }
}
