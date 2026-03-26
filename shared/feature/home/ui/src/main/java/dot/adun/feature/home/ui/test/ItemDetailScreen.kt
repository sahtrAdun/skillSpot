package dot.adun.feature.home.ui.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.core.ui.core.StateViewModel
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun ItemDetailScreen(
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .surface(
                color = AppTheme.colors.layer.background,
                padding = AppTheme.paddings.inset.screen
            )
    ) {
        Text(
            text = "Hello World!",
            style = AppTheme.typography.subhead1,
            color = AppTheme.colors.text.primary,
            modifier = Modifier
                .clickable { /*todo*/ }
        )
    }
}

@Stable
@HiltViewModel
class ItemDetailViewModel @Inject constructor() : StateViewModel<TestVS, TestVI, Unit>(TestVS) {
    override val intents = TestVI()

    init {
        viewModelScope.launch {
            delay(2000L)
            emitResult(Unit)
        }
    }
}

class TestVI : BaseViewIntents()

@Immutable
data object TestVS
