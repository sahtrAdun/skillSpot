package dot.adun.core.ui.core.event.dialog

import androidx.compose.runtime.Immutable

sealed interface DialogButtonsConfig {
    @Immutable
    data class Confirm(
        val onConfirm: () -> Unit,
        val onCancel: () -> Unit,
    ) : DialogButtonsConfig

    @Immutable
    data class Info(
        val onOk: () -> Unit
    ) : DialogButtonsConfig

    @Immutable
    data class Custom(
        val buttons: List<DialogButton>
    ) : DialogButtonsConfig

    data object None : DialogButtonsConfig
}
