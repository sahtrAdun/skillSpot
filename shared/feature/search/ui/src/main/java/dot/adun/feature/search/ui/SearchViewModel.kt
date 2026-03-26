package dot.adun.feature.search.ui

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.StateViewModel
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@Stable
@HiltViewModel
class SearchViewModel @Inject constructor(
) : StateViewModel<SearchViewState, SearchViewIntents, Unit>(SearchViewState()) {
    override val intents = SearchViewIntents()

    init {
        onIntent(intents.updateText) { newValue ->
            update { it.updateText(newValue) }
        }

        on(intent(intents.updateText).debounce(1.seconds)) {
            println("delayed")
        }

        onIntent(intents.validateText) {
            update { it.validate() }
        }

        onIntent(intents.navigateBack) {
            navigateBack()
        }
    }
}

sealed interface SearchScreenResult {
    data object Finish : SearchScreenResult
}
