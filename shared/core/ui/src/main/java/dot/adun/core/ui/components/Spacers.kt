package dot.adun.core.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dot.adun.core.ui.theme.AppTheme

@Composable
fun VSpacer(height: Dp) {
    Spacer(Modifier.height(height))
}

fun LazyListScope.vSpacer(height: Dp? = null) {
    item { Spacer(Modifier.height(height ?: AppTheme.paddings.space.regular)) }
}

@Composable
fun HSpacer(width: Dp) {
    Spacer(Modifier.width(width))
}

fun LazyListScope.hSpacer(width: Dp? = null) {
    item { Spacer(Modifier.width(width ?: AppTheme.paddings.space.regular)) }
}

@Composable
fun RowScope.WSpacer(weight: Float = 1f) {
    Spacer(Modifier.weight(weight))
}

@Composable
fun ColumnScope.WSpacer(weight: Float = 1f) {
    Spacer(Modifier.weight(weight))
}
