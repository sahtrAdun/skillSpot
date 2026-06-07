package dot.adun.feature.authorized.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.UserRoleResolver
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.SimpleLayout
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.domain.entity.ActiveProject
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.component.details.InitialsAvatar
import dot.adun.feature.authorized.ui.mappers.buildAmount
import dot.adun.feature.authorized.ui.screen.active.ActiveViewIntents

@Composable
fun ActiveLayout(
    vacancies: List<Vacancy>,
    activeProjects: List<ActiveProject>,
    loadState: LoadState,
    completingProjectId: String?,
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
                        AttachmentBox(
                            modifier = Modifier.fillMaxWidth(),
                            item = {
                                VacancyItem(
                                    vacancy = vacancy,
                                    onClick = { intents.openDetails(true to vacancy.id) }
                                )
                            },
                            attachment = {
                                ChatActionButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { intents.openChat(vacancy.id) },
                                )
                            },
                        )
                    }
                },
                forCustomers = { contentModifier ->
                    SimpleLayout(
                        items = activeProjects,
                        padding = padding,
                        modifier = contentModifier
                    ) { project, _ ->
                        AttachmentBox(
                            modifier = Modifier.fillMaxWidth(),
                            item = {
                                ActiveProjectItem(
                                    project = project,
                                    onClick = { intents.openDetails(true to project.vacancyId) }
                                )
                            },
                            attachment = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        AppTheme.paddings.space.small
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.paddings.inset.content),
                                ) {
                                    ChatActionButton(
                                        modifier = Modifier.weight(1f),
                                        onClick = { intents.openChat(project.projectId) },
                                    )
                                    PrimaryButton(
                                        state = rememberButtonState(
                                            loading = completingProjectId == project.projectId,
                                        ),
                                        clickable = Clickable.of {
                                            intents.complete(project.projectId)
                                        },
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Text(text = stringResource(R.string.action_complete))
                                    }
                                }
                            },
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ActiveProjectItem(
    project: ActiveProject,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        modifier = Modifier
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(AppTheme.paddings.inset.content),
    ) {
        Text(
            text = project.vacancyTitle,
            style = AppTheme.typography.subhead3,
            color = AppTheme.colors.text.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = buildAmount(project.vacancyCurrency, project.vacancyBudget),
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.text.primary,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            InitialsAvatar(name = project.freelancerName, size = 36.dp)
            HSpacer(AppTheme.paddings.space.small)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.freelancerName?.takeIf { it.isNotBlank() }
                        ?: project.resumeTitle,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = project.resumeTitle,
                    style = AppTheme.typography.caption2,
                    color = AppTheme.colors.text.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun ChatActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = AppTheme.paddings.space.xs,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickableEffect(Clickable.of(onClick))
            .padding(vertical = 14.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = null,
            tint = AppTheme.colors.text.accent,
            modifier = Modifier.size(18.dp),
        )
        Text(
            text = stringResource(R.string.action_chat),
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text.accent,
        )
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
