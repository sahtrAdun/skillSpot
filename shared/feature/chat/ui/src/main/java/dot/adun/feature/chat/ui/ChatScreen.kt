package dot.adun.feature.chat.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.IcButton
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.chat.domain.entity.Message
import dot.adun.feature.chat.ui.mappers.formatTime
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
) = AppScreen(viewModel) { state, intents ->
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.chat_title),
                onBackClick = intents.navigateBack,
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.top),
        ) {
            Column {
                if (state.messages.isEmpty()) {
                    EmptyState(
                        bottomPadding = padding.bottom,
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    MessagesList(
                        messages = state.messages,
                        currentUserId = state.currentUserId,
                        hasMore = state.hasMore,
                        loadMoreState = state.loadMoreState,
                        topPadding = padding.top,
                        bottomPadding = padding.bottom,
                        onLoadMore = intents.loadMore,
                        modifier = Modifier.weight(1f),
                    )
                }

                ChatInput(
                    value = state.input,
                    canSend = state.canSend,
                    sending = state.sendState.isLoading,
                    onValueChange = intents.changeInput,
                    onSend = intents.send,
                )
            }
        }
    }
}

@Composable
private fun MessagesList(
    messages: List<Message>,
    currentUserId: String?,
    hasMore: Boolean,
    loadMoreState: dot.adun.core.domain.entity.LoadState,
    topPadding: Dp,
    bottomPadding: Dp,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(listState, hasMore) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (hasMore && lastIndex != null && lastIndex >= messages.lastIndex) {
                    onLoadMore()
                }
            }
    }

    val newestId = messages.firstOrNull()?.id
    LaunchedEffect(newestId) {
        if (newestId != null && listState.firstVisibleItemIndex <= 1) {
            listState.animateScrollToItem(0)
        }
    }

    val showJumpToLatest by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyColumn(
            state = listState,
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding + 8.dp,
                bottom = bottomPadding + 8.dp,
            ),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(messages, key = { it.id }) { message ->
                MessageBubble(
                    message = message,
                    isOwn = message.senderId == currentUserId,
                )
            }

            if (loadMoreState.isLoading) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Loader(appearance = LoaderAppearance.Solid(AppTheme.colors.layer.primary))
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showJumpToLatest,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            IcButton(
                vector = Icons.Default.KeyboardArrowDown,
                colors = AppTheme.presets.buttons.icon.primary,
                onClick = { scope.launch { listState.animateScrollToItem(0) } },
                contentDescription = stringResource(R.string.chat_scroll_to_latest),
            )
        }
    }
}

@Composable
private fun MessageBubble(
    message: Message,
    isOwn: Boolean,
) {
    Column(
        horizontalAlignment = if (isOwn) Alignment.End else Alignment.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        val senderName = message.senderName
        if (!isOwn && !senderName.isNullOrBlank()) {
            Text(
                text = senderName,
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.accent,
                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp),
            )
        }

        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .surface(
                    color = if (isOwn) AppTheme.colors.layer.primaryTranslucent
                        else AppTheme.colors.layer.onSurface,
                    shape = AppTheme.shapes.medium,
                    padding = AppTheme.paddings.inset.content,
                ),
        ) {
            Text(
                text = message.content,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.align(messageAlignment(isOwn)),
            )
            VSpacer(2.dp)
            Text(
                text = message.createdAt.formatTime(),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.hint,
                modifier = Modifier.align(messageAlignment(isOwn)),
            )
        }
    }
}

@Composable
private fun ChatInput(
    value: String,
    canSend: Boolean,
    sending: Boolean,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .imePadding()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            SimpleTextField(
                data = TextFieldData(value = value),
                onValueChange = onValueChange,
                placeholder = stringResource(R.string.chat_input_hint),
                borderVisibility = BorderVisibility.Always,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        HSpacer(8.dp)

        IcButton(
            vector = Icons.AutoMirrored.Filled.Send,
            colors = AppTheme.presets.buttons.icon.primary,
            enabled = canSend && !sending,
            onClick = onSend,
        )
    }
}

@Composable
private fun EmptyState(
    bottomPadding: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = bottomPadding)
            .padding(24.dp),
    ) {
        Text(
            text = stringResource(R.string.chat_empty),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.hint,
            textAlign = TextAlign.Center,
        )
    }
}

fun messageAlignment(isOwn: Boolean): Alignment.Horizontal {
    return if (isOwn) {
        Alignment.End
    } else {
        Alignment.Start
    }
}
