package dot.adun.feature.authorized.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import androidx.compose.foundation.layout.Column
import dot.adun.feature.authorized.ui.R

@Composable
fun LeaveReviewDialog(
    submitState: LoadState,
    onSubmit: (rating: Int, comment: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
            modifier = Modifier
                .fillMaxWidth()
                .surface(
                    color = AppTheme.colors.layer.surface,
                    shape = AppTheme.shapes.large,
                    padding = AppTheme.paddings.inset.content,
                ),
        ) {
            Text(
                text = stringResource(R.string.review_title),
                style = AppTheme.typography.subhead2,
                color = AppTheme.colors.text.primary,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs)) {
                (1..5).forEach { star ->
                    Icon(
                        imageVector = if (star <= rating) Icons.Filled.Star
                        else Icons.Filled.StarBorder,
                        contentDescription = null,
                        tint = AppTheme.colors.icon.accent,
                        modifier = Modifier
                            .size(32.dp)
                            .clickableEffect(Clickable.of { rating = star }),
                    )
                }
            }

            SimpleTextField(
                data = TextFieldData(value = comment),
                onValueChange = { comment = it },
                placeholder = stringResource(R.string.review_comment_hint),
                borderVisibility = BorderVisibility.Always,
                minLines = 5,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
            )

            VSpacer(AppTheme.paddings.space.xs)

            PrimaryButton(
                state = rememberButtonState(loading = submitState.isLoading),
                clickable = Clickable.of { onSubmit(rating, comment) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.review_submit))
            }
        }
    }
}
