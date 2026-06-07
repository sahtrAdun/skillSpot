package dot.adun.core.ui.components.textFields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.TextFieldButton
import dot.adun.core.ui.components.textFields.core.BaseTextField
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.theme.presets.TextFieldsColorPresets

@Composable
fun SimpleTextField(
    data: TextFieldData,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    onLeadingClick: () -> Unit = {},
    trailingIcon: Int? = null,
    onTrailingClick: () -> Unit ={},
    placeholder: String? = null,
    enabled: Boolean = data.enabled,
    readOnly: Boolean = !enabled,
    actions: KeyboardActions? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    borderVisibility: BorderVisibility = BorderVisibility.Always,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colorPreset: TextFieldsColorPresets.AdunTextFieldColors = AppTheme.presets.textFields.common,
    maxLines: Int = 1,
    minLines: Int = 1,
) {
    BaseTextField(
        modifier = modifier,
        data = data,
        onValueChange = onValueChange,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = readOnly,
        actions = actions,
        keyboardType = keyboardType,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        borderVisibility = borderVisibility,
        colorPreset = colorPreset,
        maxLines = maxLines,
        minLines = minLines,
        leadingContent = leadingIcon?.let {
            { tint ->
                TextFieldButton(
                    icon = leadingIcon,
                    color = tint,
                    onClick = onLeadingClick,
                    description = "text field leading icon"
                )
            }
        },
        trailingContent = trailingIcon?.let {
            { tint ->
                TextFieldButton(
                    icon = trailingIcon,
                    color = tint,
                    onClick = onTrailingClick,
                    description = "text field trailing icon"
                )
            }
        }
    )
}
