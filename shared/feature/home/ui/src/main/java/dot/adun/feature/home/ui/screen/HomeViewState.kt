package dot.adun.feature.home.ui.screen

import androidx.compose.runtime.Immutable

@Immutable
data class HomeViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
)
