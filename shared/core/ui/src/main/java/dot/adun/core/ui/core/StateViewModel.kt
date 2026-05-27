package dot.adun.core.ui.core

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dot.adun.core.domain.TaskJob
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.error.AppError
import dot.adun.core.ui.core.event.ViewEvent
import dot.adun.core.ui.core.event.ViewModelEvent
import dot.adun.core.ui.core.event.snackbar.Snackbar
import dot.adun.core.ui.mappers.toUiError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration

@Stable
open class StateViewModel<VS, VI: BaseViewIntents, R>(initialState: VS) : ViewModel() {
    private val _state: MutableStateFlow<VS> = MutableStateFlow(initialState)
    val state: StateFlow<VS> = _state.asStateFlow()

    private val _result: Channel<R> = Channel(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val result: Flow<R> = _result.receiveAsFlow()

    private val _events: MutableSharedFlow<ViewEvent> = MutableSharedFlow(
        extraBufferCapacity = 5,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: Flow<ViewEvent> = _events.asSharedFlow()

    open val intents: VI by lazy {
        throw IllegalStateException("Intents not initialized in ${this::class.simpleName}")
    }

    protected fun update(reducer: (VS) -> VS) {
        _state.update(reducer)
    }

    protected suspend fun action(body: suspend (VS) -> Unit) {
        viewModelScope.launch {
            body(_state.value)
        }
    }

    @JvmName("Execute")
    protected fun execute(body: suspend (VS) -> Unit) {
        viewModelScope.launch { body(_state.value) }
    }

    @JvmName("ExecuteWithResult")
    protected suspend fun <T> execute(body: suspend (VS) -> T): T =
        withContext(Dispatchers.IO) { body(_state.value) }

    protected fun <T> executeBlocking(body: suspend (VS) -> T): T =
        runBlocking(Dispatchers.IO) { body(_state.value) }

    protected fun <V> on(
        flow: Flow<V>,
        block: suspend (V) -> Unit
    ) {
        flow
            .onEach { value -> block(value) }
            .catch { err ->
                Napier.e(err, "VIEW-MODEL-FLOW") { err.message.toString() }
            }
            .launchIn(viewModelScope)
    }

    protected fun intent(intent: SimpleIntent): Flow<Unit> =
        intents.events
            .filter { (i, _) -> i is SimpleIntent && i.id == intent.id }
            .onEach { (i, _) -> (i as SimpleIntent).log() }
            .map { }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> intent(intent: TypedIntent<T>): Flow<T> =
        intents.events
            .filter { (i, _) -> i is TypedIntent<*> && i.id == intent.id }
            .onEach { (i, _) -> (i as TypedIntent<T>).log() }
            .map { it.second as T }

    protected fun onIntent(intent: SimpleIntent, block: suspend () -> Unit) {
        on(intent(intent)) { block() }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> onIntent(intent: TypedIntent<T>, block: suspend (T) -> Unit) {
        on(intent(intent), block)
    }

    @OptIn(FlowPreview::class)
    protected fun <V> Duration.debounceOn(
        flow: Flow<V>,
        block: suspend (V) -> Unit
    ) { on(flow.debounce(this), block) }

    protected fun navigateBack() {
        _events.tryEmit(ViewModelEvent.NavigateBack)
    }

    protected fun emitEvent(event: ViewEvent) {
        _events.tryEmit(event)
    }

    protected fun emitResult(result: R) {
        _result.trySend(result)
    }

    protected fun <T> runJob(
        stateRead: (VS) -> LoadState,
        stateWrite: (VS, LoadState) -> VS,
        builder: TaskJob<VS, T>.() -> Unit
    ) {
        TaskJob<VS, T>(
            scope = viewModelScope,
            currentState = { state.value },
            onStateUpdate = { reducer -> update(reducer) },
            stateRead = stateRead,
            stateWrite = stateWrite
        )
            .apply(builder)
            .start()
    }

    protected fun errorSnack(error: AppError) {
        val uiError = error.toUiError()
        emitEvent(
            Snackbar(
                title = uiError.title,
                message = uiError.description,
                isError = true
            )
        )
    }

    private fun SimpleIntent.log() {
        val viewModelName = this@StateViewModel::class.java.simpleName
        Napier.d(tag = "view-model-intent") {
            "[$viewModelName] $name"
        }
    }

    private fun <T> TypedIntent<T>.log() {
        val viewModelName = this@StateViewModel::class.java.simpleName
        Napier.d(tag = "view-model-intent") {
            "[$viewModelName] $name"
        }
    }
}
