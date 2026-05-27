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
    fun disabled(): Boolean = this is Disabled
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
    return remember(loading, disabled) {
        when {
            disabled -> ButtonState.Disabled
            loading -> ButtonState.Loading
            else -> ButtonState.Enabled
        }
    }
}
