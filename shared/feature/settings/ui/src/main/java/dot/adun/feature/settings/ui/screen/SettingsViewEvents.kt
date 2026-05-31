package dot.adun.feature.settings.ui.screen

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.ui.core.event.dialog.DialogEvent
import dot.adun.feature.settings.domain.entity.Setting
import dot.adun.feature.settings.ui.component.SettingSelectorDialogContent

fun settingsSelectorDialog(
    title: TextRef,
    options: List<Setting.Option>,
    selectedId: Int,
    onSelect: (Int) -> Unit
) = object : DialogEvent() {
    override val title: TextRef = title
    override val dismissOnBackPress: Boolean = true
    override val dismissOnOutsideClick: Boolean = true
    override val content: @Composable (ColumnScope.() -> Unit) = {
        SettingSelectorDialogContent(
            options = options,
            selectedId = selectedId,
            onSelect = onSelect
        )
    }
}
