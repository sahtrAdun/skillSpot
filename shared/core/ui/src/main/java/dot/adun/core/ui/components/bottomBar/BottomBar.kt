package dot.adun.core.ui.components.bottomBar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.strRef
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display

@Composable
fun BottomBar(
    selectedTab: BottomBarTab,
    onTabSelected: (BottomBarTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = AppTheme.colors.layer.surface,
        contentColor = AppTheme.colors.layer.primary,
        windowInsets = WindowInsets(),
        modifier = modifier,
    ) {
        BottomBarTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tabIcons[tab] ?: return@NavigationBarItem,
                        contentDescription = "bottom_bar_${tab.id}_tab_icon",
                    )
                },
                label = tabLabels[tab]?.let { label -> { Text(label.display()) } },
                colors = itemColors(),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun itemColors(): NavigationBarItemColors {
    return NavigationBarItemColors(
        selectedIndicatorColor = AppTheme.colors.layer.primary,
        selectedIconColor = AppTheme.colors.layer.onPrimary,
        selectedTextColor = AppTheme.colors.layer.onPrimary,
        unselectedIconColor = AppTheme.colors.text.tertiary,
        unselectedTextColor = AppTheme.colors.text.tertiary,
        disabledIconColor = AppTheme.colors.layer.onSurface,
        disabledTextColor = AppTheme.colors.layer.onSurface,
    )
}

private val tabIcons = mapOf(
    BottomBarTab.Home to Icons.Default.Home,
    BottomBarTab.Active to Icons.Default.AttachMoney,
    BottomBarTab.Settings to Icons.Default.Settings,
)

private val tabLabels = mapOf<BottomBarTab, TextRef>(
    BottomBarTab.Home to strRef("Home"),
    BottomBarTab.Active to strRef("Active"),
    BottomBarTab.Settings to strRef("Settings"),
)
