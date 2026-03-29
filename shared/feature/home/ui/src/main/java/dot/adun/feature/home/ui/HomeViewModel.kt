package dot.adun.feature.home.ui

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.secret.SecretModel
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.home.domain.HomeModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@Stable
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val model: HomeModel,
    private val secretModel: SecretModel
) : StateViewModel<HomeViewState, HomeViewIntents, HomeScreenResult>(HomeViewState()) {
    override val intents = HomeViewIntents()

    init {
        onIntent(intents.navToSearch) {
            emitResult(HomeScreenResult.Search)
        }

        onIntent(intents.navToDetails) {
            model.test()
            secretModel.test()
        }
    }
}

sealed interface HomeScreenResult {
    data object Search : HomeScreenResult

    data object Finish : HomeScreenResult
}
