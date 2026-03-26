package dot.adun.core.ui.core

import dot.adun.core.domain.util.randomUuid
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID

abstract class BaseIntent(val id: String = UUID.randomUUID().toString())

abstract class SimpleIntent(
    val name: String
) : BaseIntent(), () -> Unit {
    abstract override fun invoke()
}

abstract class TypedIntent<T>(
    val name: String
) : BaseIntent(), (T) -> Unit {
    abstract override fun invoke(value: T)
}

class SimpleIntentImpl(
    name: String,
    private val emit: (SimpleIntent) -> Unit
) : SimpleIntent(name) {
    override fun invoke() = emit(this)
}

class TypedIntentImpl<T>(
    name: String,
    private val emit: (TypedIntent<T>, T) -> Unit
) : TypedIntent<T>(name) {
    override fun invoke(value: T) = emit(this, value)
}

open class BaseViewIntents {
    private val _events = MutableSharedFlow<Pair<Any, Any?>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    protected fun intent(name: String = randomUuid()): SimpleIntent =
        SimpleIntentImpl(name) {
            _events.tryEmit(it to Unit)
        }

    protected fun <T> typedIntent(name: String = randomUuid()): TypedIntent<T> =
        TypedIntentImpl(name) { intent, value ->
            _events.tryEmit(intent to value)
        }
}
