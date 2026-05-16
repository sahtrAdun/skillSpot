package dot.adun.core.ui.components.snackbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dot.adun.core.ui.core.event.snackbar.CustomSnackbarData
import dot.adun.core.ui.core.event.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CustomSnackbarHostState {
    private val mutex = Mutex()

    var currentSnackbarData by mutableStateOf<CustomSnackbarData?>(null)
        private set

    suspend fun showSnackbar(snackbar: Snackbar) {
        mutex.withLock {
            try {
                suspendCancellableCoroutine<Unit> { continuation ->
                    currentSnackbarData = object : CustomSnackbarData {
                        override val visuals: Snackbar = snackbar
                        override fun dismiss() {
                            if (currentSnackbarData?.visuals == snackbar) {
                                currentSnackbarData = null
                                if (continuation.isActive) continuation.resume(Unit) {}
                            }
                        }
                    }

                    val timeout = snackbar.duration.inWholeMilliseconds
                    val job = MainScope().launch {
                        delay(timeout)
                        if (continuation.isActive) {
                            currentSnackbarData = null
                            continuation.resume(Unit) {}
                        }
                    }

                    continuation.invokeOnCancellation {
                        job.cancel()
                        currentSnackbarData = null
                    }
                }
            } finally {
                currentSnackbarData = null
            }
        }
    }
}
