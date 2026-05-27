package dot.adun.core.ui.entity

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.TextRef

@Immutable
data class UiError(
    val title: TextRef,
    val description: TextRef? = null
)
