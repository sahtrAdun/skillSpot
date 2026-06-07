package dot.adun.feature.home.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.LocalUserRole
import dot.adun.core.ui.components.ContentLabel
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.OverflowPageIndicator
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.TopAppBar
import dot.adun.core.ui.components.buttons.IcButton
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.component.ResumeItem
import dot.adun.feature.authorized.ui.component.VacancyItem
import dot.adun.feature.home.ui.screen.HomeViewIntents

@Composable
fun HomeLayout(
    loadState: LoadState,
    refreshing: Boolean,
    vacancies: List<Vacancy>,
    resumes: List<Resume>,
    itemsSize: Int,
    intents: HomeViewIntents
) {
    AdunScaffold(
        refreshing = refreshing,
        onRefresh = intents.refresh,
        appBar = {
            TopAppBar(
                leadingContent = {
                    IcButton(
                        vector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        onClick = intents.openProfile,
                        colors = AppTheme.presets.buttons.icon.regular,
                        modifier = Modifier.border(
                            color = AppTheme.colors.border.primary,
                            shape = CircleShape,
                            width = 1.dp
                        )
                    )
                },
                trailingContent = {}
            ) {
                DummySearchField(intents.navToSearch)
            }
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.top)
        ) {
            item {
                RecommendationBlock(
                    loadState = loadState,
                    vacancies = vacancies,
                    intents = intents,
                    itemsSize = itemsSize,
                    resumes = resumes
                )
            }
            item {
                Block(resRef(Res.strings.applications), intents.openApplications)
            }
            item {
                val myItemsLabel = when (LocalUserRole.current) {
                    UserRole.Customer -> Res.strings.my_vacancies
                    else -> Res.strings.my_resumes
                }
                Block(resRef(myItemsLabel), intents.openMyItems)
            }
            item {
                val createLabel = when (LocalUserRole.current) {
                    UserRole.Customer -> Res.strings.create_vacancy
                    else -> Res.strings.create_resume
                }
                Block(resRef(createLabel), intents.openCreate)
            }
        }
    }
}

@Composable
private fun RecommendationBlock(
    loadState: LoadState,
    vacancies: List<Vacancy>,
    intents: HomeViewIntents,
    itemsSize: Int,
    resumes: List<Resume>
) {
    val role = LocalUserRole.current
    val pagerState = rememberPagerState { itemsSize }

    ContentLabel(
        label = resRef(Res.strings.for_you),
        labelModifier = Modifier.padding(horizontal = 16.dp)
    ) {
        when (loadState) {
            LoadState.Loading -> RecommendationsLoadState()
            else -> when (role) {
                UserRole.Freelancer -> {
                    Pager(
                        items = vacancies,
                        state = pagerState
                    ) { page ->
                        val vacancy = remember(page) { vacancies[page] }

                        VacancyItem(
                            vacancy = vacancy,
                            onClick = { intents.openDetails(true to vacancy.id) }
                        )
                    }
                }
                UserRole.Customer -> {
                    Pager(
                        items = resumes,
                        state = pagerState
                    ) { page ->
                        val resume = remember(page) { resumes[page] }

                        ResumeItem(
                            resume = resume,
                            onClick = { intents.openDetails(false to resume.id) }
                        )
                    }
                }
                UserRole.None -> Unit
            }
        }

        AnimatedVisibility(itemsSize > 1) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()

            ) {
                OverflowPageIndicator(
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage,
                    dotSize = 6.dp,
                    dotSpacing = 8.dp,
                    modifier = Modifier
                        .surface(
                            color = AppTheme.colors.layer.surface,
                            shape = CircleShape,
                            padding = AppTheme.paddings.inset.contentSmall
                        )
                )
            }
        }
    }
}

@Composable
private fun Block(
    label: TextRef,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = label.display(),
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text.primary,
            modifier = Modifier.weight(1f)
        )
        HSpacer(12.dp)
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = AppTheme.colors.icon.primary
        )
    }
}

@Composable
private fun <T> Pager(
    items: List<T>,
    state: PagerState = rememberPagerState { items.size },
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(
        state = state,
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp
    ) { page -> content(page) }
}

@Composable
private fun RecommendationsLoadState() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .shimmer(16.dp)
            )
        }
    }
}
