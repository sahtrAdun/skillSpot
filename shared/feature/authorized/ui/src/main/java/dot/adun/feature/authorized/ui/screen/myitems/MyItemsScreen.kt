package dot.adun.feature.authorized.ui.screen.myitems

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.VerticalList
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.buttons.SecondaryTextButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.component.ResumeItem
import dot.adun.feature.authorized.ui.component.VacancyItem
import dot.adun.core.domain.entity.LoadState as DomainLoadState

@Composable
fun MyItemsScreen(
    viewModel: MyItemsViewModel,
) = AppScreen(viewModel) { state, intents ->
    val title = when (state.userRole) {
        UserRole.Customer -> stringResource(Res.strings.my_vacancies)
        else -> stringResource(Res.strings.my_resumes)
    }

    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = title,
                onBackClick = intents.navigateBack,
            )
        }
    ) { padding ->
        MyItemsContent(
            userRole = state.userRole,
            vacancies = state.vacancies,
            resumes = state.resumes,
            loadState = state.loadState,
            loadMoreState = state.loadMoreState,
            hasMore = state.hasMore,
            isEmptyResult = state.isEmptyResult,
            topPadding = padding.top,
            bottomPadding = padding.bottom,
            onOpenDetails = { isVacancy, id -> intents.openDetails(isVacancy to id) },
            onLoadMore = intents.loadMore,
        )
    }
}

@Composable
private fun MyItemsContent(
    userRole: UserRole,
    vacancies: List<Vacancy>,
    resumes: List<Resume>,
    loadState: DomainLoadState,
    loadMoreState: DomainLoadState,
    hasMore: Boolean,
    isEmptyResult: Boolean,
    topPadding: Dp,
    bottomPadding: Dp,
    onOpenDetails: (isVacancy: Boolean, id: String) -> Unit,
    onLoadMore: () -> Unit,
) {
    if (isEmptyResult) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding)
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(R.string.my_items_empty),
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.hint,
                textAlign = TextAlign.Center,
            )
        }
        return
    }

    LoadState(
        loadState = loadState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topPadding),
    ) {
        when (userRole) {
            UserRole.Customer -> VerticalList(modifier = Modifier.fillMaxSize()) {
                items(vacancies) { vacancy ->
                    VacancyItem(
                        vacancy = vacancy,
                        onClick = { onOpenDetails(true, vacancy.id) },
                    )
                }
                if (hasMore) {
                    item { LoadMoreButton(loadMoreState = loadMoreState, onLoadMore = onLoadMore) }
                }
                item { VSpacer(bottomPadding) }
            }

            UserRole.Freelancer -> VerticalList(modifier = Modifier.fillMaxSize()) {
                items(resumes) { resume ->
                    ResumeItem(
                        resume = resume,
                        onClick = { onOpenDetails(false, resume.id) },
                    )
                }
                if (hasMore) {
                    item { LoadMoreButton(loadMoreState = loadMoreState, onLoadMore = onLoadMore) }
                }
                item { VSpacer(bottomPadding) }
            }

            UserRole.None -> Unit
        }
    }
}

@Composable
private fun LoadMoreButton(
    loadMoreState: DomainLoadState,
    onLoadMore: () -> Unit,
) {
    SecondaryTextButton(
        text = stringResource(Res.strings.load_more),
        clickable = Clickable.of(onLoadMore),
        state = rememberButtonState(loading = loadMoreState.isLoading),
        modifier = Modifier.fillMaxWidth(),
    )
}
