package dot.adun.core.ui.components.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.components.divider.HDivider
import dot.adun.core.ui.core.event.dialog.DialogButtonsConfig
import dot.adun.core.ui.core.event.dialog.DialogEvent
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display

@Composable
fun DialogComponent(
    hostState: DialogHostState,
) {
    val dialog = hostState.currentDialog ?: return

    BackHandler(enabled = true) {
        if (dialog.dismissOnBackPress) {
            dialog.onDismiss?.invoke()
            hostState.dismiss()
        }
    }

    Dialog(
        onDismissRequest = {
            dialog.onDismiss?.invoke()
            hostState.dismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = dialog.dismissOnOutsideClick,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .surface(
                    color = AppTheme.colors.layer.surface,
                    shape = AppTheme.shapes.medium
                )
        ) {
            dialog.title?.let {
                Title(it)
                HDivider()
            }
            Content(dialog)
            Buttons(dialog)
        }
    }
}

@Composable
private fun Title(
    title: TextRef,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.display(),
        style = AppTheme.typography.subhead2,
        color = AppTheme.colors.text.primary,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    )
}

@Composable
private fun ColumnScope.Content(
    dialog: DialogEvent,
    modifier: Modifier = Modifier
) {
    if (dialog.content != null) {
        dialog.content?.invoke(this)
    } else {
        dialog.message?.let { message ->
            Text(
                text = message.display(),
                style = AppTheme.typography.body1,
                color = AppTheme.colors.text.secondary,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.Buttons(
    dialog: DialogEvent,
    modifier: Modifier = Modifier
) {
    if (dialog.buttons !is DialogButtonsConfig.None) { HDivider() }
    else return

    when (val config = dialog.buttons) {
        is DialogButtonsConfig.Confirm -> ConfirmButtons(config, modifier)
        is DialogButtonsConfig.Custom -> CustomButtons(config, modifier)
        is DialogButtonsConfig.Info -> OkButton(config, modifier)
        DialogButtonsConfig.None -> Unit
    }
}

@Composable
private fun ConfirmButtons(
    config: DialogButtonsConfig.Confirm,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        DialogButton(
            text = resRef(Res.strings.button_cancel),
            color = AppTheme.colors.text.error,
            onClick = config.onCancel
        )
        DialogButton(
            text = resRef(Res.strings.button_confirm),
            color = AppTheme.colors.text.accent,
            onClick = config.onConfirm
        )
    }
}

@Composable
private fun OkButton(
    config: DialogButtonsConfig.Info,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        DialogButton(
            text = resRef(Res.strings.button_ok),
            color = AppTheme.colors.text.primary,
            onClick = config.onOk
        )
    }
}

@Composable
private fun CustomButtons(
    config: DialogButtonsConfig.Custom,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        config.buttons.forEach { button ->
            DialogButton(
                text = button.text,
                color = AppTheme.colors.text.primary,
                onClick = button.onClick,
                enabled = button.enabled
            )
        }
    }
}
