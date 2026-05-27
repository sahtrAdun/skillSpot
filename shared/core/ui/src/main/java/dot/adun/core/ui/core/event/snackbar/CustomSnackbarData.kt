package dot.adun.core.ui.core.event.snackbar

import androidx.compose.runtime.Stable

@Stable
interface CustomSnackbarData {
    val visuals: Snackbar
    fun dismiss()
}
