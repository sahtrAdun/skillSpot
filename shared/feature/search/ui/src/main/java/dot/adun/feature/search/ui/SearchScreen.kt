package dot.adun.feature.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.VerticalList
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.SecondaryTextButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.ui.component.ResumeItem
import dot.adun.feature.authorized.ui.component.VacancyItem
import dot.adun.core.domain.entity.LoadState as DomainLoadState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
) = AppScreen(viewModel) { state, intents ->
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    AdunScaffold(
        appBar = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = stringResource(Res.strings.button_cancel),
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text.accent,
                    modifier = Modifier.clickableEffect(Clickable.of(intents.cancel)),
                )
                Box(modifier = Modifier.weight(1f)) {
                    SimpleTextField(
                        data = TextFieldData(value = state.query),
                        onValueChange = intents.updateQuery,
                        placeholder = stringResource(Res.strings.search_hint),
                        borderVisibility = BorderVisibility.Always,
                        height = 44.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    )
                }
            }
        }
    ) { padding ->
        SearchResults(
            userRole = state.userRole,
            vacancies = state.vacancies,
            resumes = state.resumes,
            loadState = state.loadState,
            isEmptyResult = state.isEmptyResult,
            hasMore = state.hasMore,
            loadMoreState = state.loadMoreState,
            topPadding = padding.top,
            onOpenDetails = { isVacancy, id -> intents.openDetails(isVacancy to id) },
            onLoadMore = intents.loadMore,
        )
    }
}

@Composable
private fun SearchResults(
    userRole: UserRole,
    vacancies: List<dot.adun.feature.authorized.domain.entity.Vacancy>,
    resumes: List<dot.adun.feature.authorized.domain.entity.Resume>,
    loadState: DomainLoadState,
    isEmptyResult: Boolean,
    hasMore: Boolean,
    loadMoreState: DomainLoadState,
    topPadding: androidx.compose.ui.unit.Dp,
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
                text = stringResource(Res.strings.search_empty),
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
        onLoading = null
    ) {
        when (userRole) {
            UserRole.Freelancer -> VerticalList(modifier = Modifier.fillMaxSize()) {
                items(vacancies) { vacancy ->
                    VacancyItem(
                        vacancy = vacancy,
                        onClick = { onOpenDetails(true, vacancy.id) },
                    )
                }
                if (hasMore) {
                    item { LoadMoreButton(loadMoreState = loadMoreState, onLoadMore = onLoadMore) }
                }
            }

            UserRole.Customer -> VerticalList(modifier = Modifier.fillMaxSize()) {
                items(resumes) { resume ->
                    ResumeItem(
                        resume = resume,
                        onClick = { onOpenDetails(false, resume.id) },
                    )
                }
                if (hasMore) {
                    item { LoadMoreButton(loadMoreState = loadMoreState, onLoadMore = onLoadMore) }
                }
            }

            UserRole.None -> Unit
        }

        FullScreenLoader(loadState.isLoading)
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
