package dot.adun.core.ui.core.event.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.ui.core.event.ViewEvent

open class DialogEvent : ViewEvent {
    open val title: TextRef? = null
    open val message: TextRef? = null
    open val dismissOnBackPress: Boolean = true
    open val dismissOnOutsideClick: Boolean = false
    open val buttons: DialogButtonsConfig = DialogButtonsConfig.None
    open val onDismiss: (() -> Unit)? = null
    open val content: (@Composable ColumnScope.() -> Unit)? = null
}
