package dot.adun.feature.auth.ui.screen

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.event.dialog.DialogEvent
import dot.adun.feature.auth.ui.R
import dot.adun.feature.auth.ui.component.ChooseRoleDialogContent
import dot.adun.core.domain.entity.UserRole

fun chooseRoleDialog(
    onConfirm: (UserRole) -> Unit
) = object : DialogEvent() {
    override val title = resRef(R.string.dialog_title)
    override val dismissOnBackPress = false
    override val content: @Composable (ColumnScope.() -> Unit) = {
        ChooseRoleDialogContent(onConfirm)
    }
}
