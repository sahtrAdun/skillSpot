package dot.adun.core.ui.components.snackbar

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme

@Composable
fun Snackbar(
    title: String,
    message: String?,
    isError: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetY by remember { mutableFloatStateOf(0f) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { translationY = offsetY }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (offsetY < -100f) {
                            onDismiss()
                        } else {
                            offsetY = 0f
                        }
                    },
                    onDragCancel = {
                        offsetY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val newOffset = offsetY + dragAmount.y
                        if (newOffset <= 0f) {
                            offsetY = newOffset
                        }
                    }
                )
            }
            .statusBarsPadding()
            .displayCutoutPadding()
            .padding(16.dp)
            .surface(
                color = if (isError) AppTheme.colors.layer.error else AppTheme.colors.layer.onSurface,
                shape = AppTheme.shapes.medium,
                padding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            )
    ) {
        Text(
            text = title,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.onPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        message?.let {
            Text(
                text = it,
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.onPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
