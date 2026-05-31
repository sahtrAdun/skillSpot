package dot.adun.core.ui.components.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dot.adun.core.ui.core.event.dialog.DialogEvent

class DialogHostState {
    var currentDialog by mutableStateOf<DialogEvent?>(null)
        private set

    fun show(event: DialogEvent) {
        currentDialog = event
    }

    fun dismiss() {
        currentDialog = null
    }
}
