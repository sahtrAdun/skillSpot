package dot.adun.routing.nav3.mappers

import androidx.navigation3.runtime.NavKey
import dot.adun.core.ui.components.bottomBar.BottomBarTab
import dot.adun.feature.authorized.routing.routes.ActiveRoute
import dot.adun.feature.home.routing.routes.HomeRoute
import dot.adun.feature.settings.routing.SettingsRoute

fun BottomBarTab.toRoute(): NavKey = when (this) {
    BottomBarTab.Home -> HomeRoute()
    BottomBarTab.Active -> ActiveRoute()
    BottomBarTab.Settings -> SettingsRoute()
}
