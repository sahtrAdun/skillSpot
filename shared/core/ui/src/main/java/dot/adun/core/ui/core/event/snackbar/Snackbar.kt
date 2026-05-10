package dot.adun.core.ui.core.event.snackbar

import dot.adun.core.domain.entity.TextRef
import dot.adun.core.ui.core.event.GlobalEvent
import dot.adun.core.ui.core.event.ViewEvent
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

open class Snackbar() : ViewEvent {
    lateinit var title: TextRef
    var message: TextRef? = null
    var duration: Duration = SNACK_DURATION
    var isError: Boolean = false
    var isImportant: Boolean = false

    constructor(
        title: TextRef,
        message: TextRef?,
        isError: Boolean,
        isImportant: Boolean,
        duration: Duration
    ) : this() {
        this.title = title
        this.message = message
        this.isError = isError
        this.isImportant = isImportant
        this.duration = duration
    }

    constructor(
        title: TextRef,
        message: TextRef?,
        isError: Boolean,
        isImportant: Boolean
    ) : this() {
        this.title = title
        this.message = message
        this.isError = isError
        this.isImportant = isImportant
        this.duration = if (isImportant) IMPORTANT_SNACK_DURATION else SNACK_DURATION
    }

    constructor(
        title: TextRef,
        message: TextRef?,
    ) : this() {
        this.title = title
        this.message = message
    }

    constructor(
        title: TextRef,
        message: TextRef?,
        duration: Duration
    ) : this() {
        this.title = title
        this.message = message
        this.duration = duration
    }

    constructor(
        title: TextRef,
        message: TextRef?,
        isError: Boolean,
    ) : this() {
        this.title = title
        this.message = message
        this.isError = isError
    }

    constructor(
        title: TextRef,
        message: TextRef?,
        isError: Boolean,
        duration: Duration
    ) : this() {
        this.title = title
        this.message = message
        this.isError = isError
        this.duration = duration
    }
}

class GlobalSnackbar(
    title: TextRef,
    message: TextRef? = null,
    duration: Duration = SNACK_DURATION,
    isError: Boolean = false,
    isImportant: Boolean = false
) : GlobalEvent, Snackbar(
    title = title,
    message = message,
    isError = isError,
    isImportant = isImportant,
    duration = duration
)

private val SNACK_DURATION = 15.seconds
private val IMPORTANT_SNACK_DURATION = 10.seconds
