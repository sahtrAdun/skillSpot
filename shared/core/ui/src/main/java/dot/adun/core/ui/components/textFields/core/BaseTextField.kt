package dot.adun.core.ui.components.textFields.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.validation.Explanation
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.base.HSpacer
import dot.adun.core.ui.components.base.VSpacer
import dot.adun.core.ui.components.buttons.TextFieldButton
import dot.adun.core.ui.components.text.OverflowText
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.theme.presets.TextFieldsColorPresets

@Composable
fun BaseTextField(
    data: TextFieldData,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingContent: (@Composable (tint: Color) -> Unit)? = null,
    trailingContent: (@Composable (tint: Color) -> Unit)? = null,
    placeholder: String? = null,
    enabled: Boolean = data.enabled,
    readOnly: Boolean = !enabled,
    actions: KeyboardActions? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    contentPaddings: PaddingValues = PaddingValues(12.dp),
    borderVisibility: BorderVisibility = BorderVisibility.Newer,
    colorPreset: TextFieldsColorPresets.AdunTextFieldColors = AppTheme.presets.textFields.common
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    val colors = TextFieldsColorPresets.rememberTextFieldColors(data, colorPreset, isFocused)
    val tint by animateColorAsState(colors.text.copy(alpha = 0.5f))
    val cursorColor = SolidColor(
        if (data.hasError) colors.text else colors.cursor
    )

    Column {
        BasicTextField(
            value = data.value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            visualTransformation = visualTransformation,
            textStyle = AppTheme.typography.body1.copy(color = colors.text),
            cursorBrush = cursorColor,
            keyboardActions = actions ?: KeyboardActions { focusManager.clearFocus() },
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .surface(
                            color = animateColorAsState(colors.background).value,
                            shape = AppTheme.shapes.medium,
                            padding = contentPaddings,
                            border = Border(
                                color = animateColorAsState(
                                    colors.border.copy(alpha = 0.5f)
                                ).value,
                                shape = AppTheme.shapes.medium
                            )
                                .takeIf { borderVisibility.visible(data.hasError) }
                        )
                ) {
                    if (leadingContent != null) {
                        leadingContent(tint)
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (data.value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = AppTheme.typography.body1,
                                color = tint,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val enterTransition =
                            if (trailingContent == null) fadeIn() + slideInHorizontally { it }
                            else fadeIn()
                        val exitTransition =
                            if (trailingContent == null) fadeOut() + slideOutHorizontally { it / 2 }
                            else fadeOut()

                        AnimatedContent(
                            targetState = data.value.isEmpty(),
                            transitionSpec = { enterTransition togetherWith exitTransition },
                            contentAlignment = Alignment.Center
                        ) { empty ->
                            if (!empty) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TextFieldButton(
                                        icon = Res.drawable.ic_backspace_fill_24,
                                        color = tint,
                                        onClick = { onValueChange("") },
                                        description = "text field clear icon"
                                    )
                                    if (trailingContent != null) {
                                        HSpacer(12.dp)
                                    }
                                }
                            }
                        }
                        if (trailingContent != null) {
                            trailingContent(tint)
                        }
                    }
                }

            },
            modifier = modifier
                .height(52.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        )
        AnimatedContent(
            targetState = data.helper,
            contentKey = { it?.explanation?.contentKey() },
            transitionSpec = {
                fadeIn() + slideInVertically { -it } togetherWith
                        fadeOut() + slideOutVertically { -it }
            }
        ) { helper ->
            if (helper != null) {
                Column {
                    VSpacer(8.dp)
                    TextFieldHelper(helper)
                }
            }
        }
    }
}

@Composable
private fun TextFieldHelper(helper: TextFieldData.Helper) {
    val color = rememberHelperColors(helper)

    OverflowText(
        text = helper.text(),
        style = AppTheme.typography.body2,
        color = color,
        maxLines = 2
    )
}

@Composable
private fun rememberHelperColors(helper: TextFieldData.Helper): Color {
    val color = when (helper.explanation.highlight) {
        Explanation.HighlightLevel.Error -> AppTheme.colors.text.error
        Explanation.HighlightLevel.Warning -> AppTheme.colors.text.warning
        Explanation.HighlightLevel.Info -> AppTheme.colors.text.tertiary
        Explanation.HighlightLevel.Highlight -> AppTheme.colors.text.accent
    }

    return remember(helper.explanation.highlight) { color }
}
