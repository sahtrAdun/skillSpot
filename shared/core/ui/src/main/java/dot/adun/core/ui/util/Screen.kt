package dot.adun.core.ui.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpOffset

@Composable
fun rememberScreenPaddings(): DpOffset {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() +
            WindowInsets.displayCutout.asPaddingValues().calculateTopPadding()

    return remember(bottomPadding, topPadding) { DpOffset(bottomPadding, topPadding) }
}
