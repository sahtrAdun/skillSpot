package dot.adun.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.mappers.toUiError
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display

@Composable
fun LoadState(
    loadState: LoadState,
    modifier: Modifier = Modifier,
    onError: @Composable (LoadState.Error) -> Unit = { LoadStateContent.ErrorState(it) },
    onLoading: @Composable (() -> Unit)? = { LoadStateContent.LoadState() },
    onDone: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        when (loadState) {
            is LoadState.Error -> onError(loadState)
            LoadState.Loading -> onLoading?.invoke() ?: onDone()
            LoadState.Done,
            LoadState.NotStarted -> onDone()
        }
    }
}

object LoadStateContent {
    val loadingHeight = 96.dp

    @Composable
    fun ErrorState(state: LoadState.Error) {
        val error = remember(state.error) { state.error.toUiError() }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "error state icon",
                tint = AppTheme.colors.icon.secondary
            )

            Text(
                text = error.title.display(),
                color = AppTheme.colors.text.secondary,
                style = AppTheme.typography.body1,
                textAlign = TextAlign.Center
            )

            error.description?.let { desc ->
                Text(
                    text = desc.display(),
                    color = AppTheme.colors.text.tertiary,
                    style = AppTheme.typography.body3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun LoadState() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(loadingHeight)
                .shimmer(16.dp)
        )
    }
}
