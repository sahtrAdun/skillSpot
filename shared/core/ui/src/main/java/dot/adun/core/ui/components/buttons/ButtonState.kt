package dot.adun.core.ui.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember

@Immutable
sealed interface ButtonState {
    data object Enabled : ButtonState
    data object Disabled : ButtonState

    data object Loading : ButtonState

    fun enabled(): Boolean = this is Enabled
    fun isLoading(): Boolean = this is Loading

    fun load(): ButtonState {
        return Loading
    }

    fun toggle(): ButtonState {
        return if (this.enabled()) Disabled else Enabled
    }
}

@Composable
fun rememberButtonState(
    loading: Boolean = false,
    disabled: Boolean = false
): ButtonState {
    return remember {
        when {
            loading -> ButtonState.Loading
            disabled -> ButtonState.Disabled
            else -> ButtonState.Enabled
        }
    }
}
