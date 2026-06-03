package dot.adun.feature.home.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.feature.home.ui.components.HomeLayout

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) = AppScreen(viewModel) { state, intents ->
    HomeLayout(
        loadState = state.loadState,
        refreshing = state.refreshing,
        vacancies = state.recommendedVacancies.repeat(6),
        resumes = state.recommendedResumes.repeat(6),
        itemsSize = state.itemsSize * 6,
        intents = intents
    )
}

fun <T> List<T>.repeat(times: Int): List<T> {
    val list = mutableListOf<T>()
    repeat(times) {
        list.addAll(this)
    }
    return list
}
