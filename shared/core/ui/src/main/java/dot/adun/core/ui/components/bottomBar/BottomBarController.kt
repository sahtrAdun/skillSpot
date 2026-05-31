package dot.adun.core.ui.components.bottomBar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class BottomBarTab(
    val id: String,
) {
    Home("home"),
    Active("active"),
    Settings("settings")
}

class BottomBarController(initialTab: BottomBarTab = BottomBarTab.Home) {
    private var _height by mutableStateOf(0.dp)
    var selectedTab by mutableStateOf(initialTab)
        private set
    var isVisible by mutableStateOf(false)

    val height: Dp get() = if (isVisible) _height else 0.dp

    fun setHeight(height: Dp) { _height = height }
    fun selectTab(tab: BottomBarTab) { selectedTab = tab }
    fun show() { isVisible = true }
    fun hide() { isVisible = false }
}

val LocalBottomBarController = staticCompositionLocalOf<BottomBarController> {
    error("LocalBottomBarController not provided")
}
