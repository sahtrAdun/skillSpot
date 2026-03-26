package dot.adun.core.ui.entity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

@Immutable
data class ScreenActions(
    val scope: CoroutineScope,
    val focusManager: FocusManager,
    val keyboard: SoftwareKeyboardController?
) {
    fun clearFocus() {
        keyboard?.hide()
        focusManager.clearFocus()
    }
}

@Composable
fun rememberScreenActions(): ScreenActions {
    return ScreenActions(
        scope = rememberCoroutineScope(),
        focusManager = LocalFocusManager.current,
        keyboard = LocalSoftwareKeyboardController.current
    )
}
