package dot.adun.feature.authorized.routing.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.routing.Route
import dot.adun.feature.authorized.ui.screen.myitems.MyItemsScreen
import dot.adun.feature.authorized.ui.screen.myitems.MyItemsViewModel
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MyItemsRoute(
    override val id: String = "my_items_route"
) : Route<MyItemsViewModel> {
    @Composable
    override fun Screen(viewModel: MyItemsViewModel) {
        MyItemsScreen(viewModel = viewModel)
    }

    @Composable
    override fun viewModel(): MyItemsViewModel = hiltViewModel()
}
