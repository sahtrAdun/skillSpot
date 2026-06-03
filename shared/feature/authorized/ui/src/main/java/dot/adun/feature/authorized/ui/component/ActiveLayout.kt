package dot.adun.feature.authorized.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.UserRoleResolver
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.SimpleLayout
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.screen.active.ActiveViewIntents

@Composable
fun ActiveLayout(
    vacancies: List<Vacancy>,
    activeProjects: List<ActiveProject>,
    loadState: LoadState,
    intents: ActiveViewIntents
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(Res.strings.in_progress),
                onBackClick = {},
                leadingContent = null
            )
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
            alignment = Alignment.TopCenter,
            modifier = Modifier.offset(y = -(MaterialDecorator.Size.Large.value / 6))
        )

        LoadState(
            loadState = loadState,
            modifier = Modifier.padding(top = padding.top),
            onLoading = { ItemsLoadState() }
        ) {
            UserRoleResolver(
                modifier = Modifier.padding(AppTheme.paddings.inset.list),
                forFreelancers = { contentModifier ->
                    SimpleLayout(
                        items = vacancies,
                        padding = padding,
                        modifier = contentModifier
                    ) { vacancy, _ ->
                        VacancyItem(
                            vacancy = vacancy,
                            onClick = { intents.openDetails(true to vacancy.id) }
                        )
                    }
                },
                forCustomers = { contentModifier ->
                    SimpleLayout(
                        items = activeProjects,
                        padding = padding,
                        modifier = contentModifier
                    ) { project, index ->

                    }
                }
            )
        }
    }
}

@Composable
private fun ItemsLoadState() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .shimmer(16.dp)
            )
        }
    }
}
