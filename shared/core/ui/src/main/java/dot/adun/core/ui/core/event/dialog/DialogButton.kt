package dot.adun.core.ui.core.event.dialog

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.TextRef

@Immutable
data class DialogButton(
    val text: TextRef,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)
