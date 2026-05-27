package dot.adun.feature.auth.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.SecondaryButton
import dot.adun.core.ui.components.divider.HDivider
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.entity.ScreenActions
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.auth.ui.R
import dot.adun.feature.auth.ui.screen.Intents
import kotlin.time.Duration.Companion.seconds

@Composable
fun AuthLayout(
    actions: ScreenActions,
    intents: Intents,
    modifier: Modifier = Modifier
) {
    AdunScaffold(
        screenActions = actions,
        modifier = modifier,
    ) { offset ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .background(AppTheme.colors.layer.surface)
        )
        MaterialShape(
            size = MaterialDecorator.Size.Custom(512.dp),
            color = AppTheme.colors.layer.surface,
            alignment = Alignment.TopCenter,
            modifier = Modifier.padding(top = 256.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AutoScrollingVerticalPager(
                    pageCount = pages.size,
                    interval = 5.seconds,
                    contentPadding = PaddingValues(
                        bottom = 48.dp,
                        top = 144.dp + offset.y
                    ),
                    pageSize = PageSize.Fixed(256.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight()
                ) { index ->
                    Page(
                        page = pages[index],
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                SecondaryButton(
                    clickable = Clickable.of(intents.login),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(Res.strings.button_login))
                }
                HDivider(
                    text = stringResource(Res.strings.or),
                    color = AppTheme.colors.text.tertiary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                PrimaryButton(
                    clickable = Clickable.of(intents.register),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(Res.strings.button_register))
                }
            }
        }
    }
}

@Composable
private fun Page(
    page: Page,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = page.title.display(),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.headline5,
            textAlign = TextAlign.Center
        )
        Text(
            text = page.content.display(),
            color = AppTheme.colors.text.secondary,
            style = AppTheme.typography.subhead3,
            textAlign = TextAlign.Center
        )
    }
}

private val pages = listOf(
    Page(
        title = resRef(R.string.pager_1_title),
        content = resRef(R.string.pager_1_desc)
    ),
    Page(
        title = resRef(R.string.pager_2_title),
        content = resRef(R.string.pager_2_desc)
    ),
    Page(
        title = resRef(R.string.pager_3_title),
        content = resRef(R.string.pager_3_desc)
    ),
    Page(
        title = resRef(R.string.pager_4_title),
        content = resRef(R.string.pager_4_desc)
    ),
    Page(
        title = resRef(R.string.pager_5_title),
        content = resRef(R.string.pager_5_desc)
    ),
)

@Immutable
private data class Page(
    val title: TextRef,
    val content: TextRef
)
