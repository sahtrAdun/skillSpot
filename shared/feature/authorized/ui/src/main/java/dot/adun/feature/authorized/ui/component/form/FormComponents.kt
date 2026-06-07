package dot.adun.feature.authorized.ui.component.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.components.textFields.core.TextFieldDefaults
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.ui.R

/** Surface card wrapping a group of form fields with consistent spacing. */
@Composable
fun FormSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.large,
                padding = AppTheme.paddings.inset.content,
            )
    ) {
        content()
    }
}

/** Text input with an "Add" button and a flow of removable skill chips. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsField(
    input: String,
    skills: List<String>,
    onInputChange: (String) -> Unit,
    onAdd: () -> Unit,
    onRemove: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            val fieldModifier = if (focusRequester != null) {
                Modifier.fillMaxWidth().focusRequester(focusRequester)
            } else {
                Modifier.fillMaxWidth()
            }

            Box(modifier = Modifier.weight(1f)) {
                SimpleTextField(
                    data = TextFieldData(value = input),
                    onValueChange = onInputChange,
                    placeholder = stringResource(R.string.skill_input_hint),
                    enabled = enabled,
                    borderVisibility = BorderVisibility.Always,
                    modifier = fieldModifier,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(TextFieldDefaults.height)
                    .surface(
                        color = AppTheme.colors.layer.primaryTranslucent,
                        shape = AppTheme.shapes.small,
                    )
                    .clickableEffect(Clickable.of(onAdd))
                    .padding(AppTheme.paddings.full.small)
            ) {
                Text(
                    text = stringResource(R.string.add),
                    color = AppTheme.colors.text.accent,
                    style = AppTheme.typography.body1,
                )
            }
        }

        if (skills.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            ) {
                skills.forEach { skill ->
                    Text(
                        text = "$skill x",
                        style = AppTheme.typography.caption2,
                        color = AppTheme.colors.text.accent,
                        modifier = Modifier
                            .surface(
                                color = AppTheme.colors.layer.primaryTranslucent,
                                shape = AppTheme.shapes.small,
                                padding = AppTheme.paddings.full.xs,
                            )
                            .clickableEffect(Clickable.of { onRemove(skill) }),
                    )
                }
            }
        }
    }
}

/** A single-select row of chips. */
@Composable
fun <T> SingleSelectChips(
    options: List<T>,
    selected: T,
    label: @Composable (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = modifier,
    ) {
        options.forEach { option ->
            SelectableChip(
                text = label(option),
                selected = option == selected,
                onClick = { onSelect(option) },
            )
        }
    }
}

/** A multi-select flow of chips. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> MultiSelectChips(
    options: List<T>,
    selected: List<T>,
    label: @Composable (T) -> String,
    onToggle: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = modifier,
    ) {
        options.forEach { option ->
            SelectableChip(
                text = label(option),
                selected = option in selected,
                onClick = { onToggle(option) },
            )
        }
    }
}

@Composable
private fun SelectableChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = AppTheme.typography.caption1,
        color = if (selected) AppTheme.colors.text.accent else AppTheme.colors.text.primary,
        modifier = Modifier
            .surface(
                color = if (selected) AppTheme.colors.layer.primaryTranslucent
                    else AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.small,
                padding = AppTheme.paddings.full.small,
            )
            .clickableEffect(Clickable.of(onClick)),
    )
}
