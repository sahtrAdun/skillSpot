package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.divider.HDivider
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.buildPaymentLabel
import dot.adun.feature.authorized.ui.mappers.formatDateTime
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.details.vacancy.VacancyDetailsViewIntents

@Composable
fun VacancyDetailsContent(
    vacancy: Vacancy?,
    creator: PublicProfile?,
    loadState: LoadState,
    creatorLoadState: LoadState,
    canEdit: Boolean,
    canApply: Boolean,
    applied: Boolean,
    intents: VacancyDetailsViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.vacancy_details_title),
                onBackClick = intents.navigateBack,
                trailingContent = if (canEdit) {
                    {
                        Text(
                            text = stringResource(R.string.edit),
                            color = AppTheme.colors.text.accent,
                            style = AppTheme.typography.body1,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .click(Clickable.of(intents.edit)),
                        )
                    }
                } else null,
            )
        },
        bottomContent = if (canApply || applied) {
            {
                PrimaryButton(
                    state = rememberButtonState(disabled = applied),
                    clickable = Clickable.of(intents.openApplySheet),
                    modifier = Modifier
                        .imePadding()
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp)
                        .padding(bottom = 12.dp),
                ) {
                    Text(
                        text = stringResource(
                            if (applied) R.string.vacancy_applied else R.string.vacancy_apply
                        )
                    )
                }
            }
        } else null,
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
            alignment = Alignment.BottomCenter
        )

        LoadState(
            loadState = loadState,
            modifier = Modifier.padding(top = padding.top),
            onLoading = { DetailsLoader() }
        ) {
            vacancy?.let { vacancy ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                ) {
                    VacancyTitleCard(vacancy)
                    VacancyDescriptionCard(vacancy)
                    if (!canEdit) {
                        CreatorCard(
                            title = stringResource(R.string.vacancy_author),
                            creator = creator,
                            loadState = creatorLoadState,
                            onClick = creator?.let { author -> { intents.openProfile(author.id) } },
                        )
                    }
                    VacancyMetaCard(vacancy)
                    VSpacer(padding.bottom)
                }
            }
        }
    }
}

@Composable
private fun VacancyTitleCard(vacancy: Vacancy) {
    DetailCard {
        Text(
            text = vacancy.info.title,
            style = AppTheme.typography.subhead1,
            color = AppTheme.colors.text.primary,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xxs)) {
            Text(
                text = stringResource(R.string.status_label),
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.secondary,
            )
            Text(
                text = vacancy.status.label.display(),
                style = AppTheme.typography.body2,
                color = when (vacancy.status) {
                    Vacancy.Status.Open -> AppTheme.colors.layer.positive
                    Vacancy.Status.InProgress -> AppTheme.colors.text.warning
                    Vacancy.Status.Closed -> AppTheme.colors.text.disabled
                },
            )
        }

        Text(
            text = buildPaymentLabel(vacancy.payment),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.primary,
        )
    }
}

@Composable
private fun VacancyMetaCard(vacancy: Vacancy) {
    DetailCard {
        Text(
            text = stringResource(R.string.vacancy_views, vacancy.viewsCount),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.hint,
        )
        Text(
            text = stringResource(R.string.created_at, vacancy.createdAt.formatDateTime()),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.hint,
        )
        Text(
            text = stringResource(R.string.updated_at, vacancy.updatedAt.formatDateTime()),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.hint,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VacancyDescriptionCard(vacancy: Vacancy) {
    DetailCard {
        SectionTitle(stringResource(R.string.vacancy_description))
        Text(
            text = vacancy.info.description,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.secondary,
        )

        HDivider()

        SectionTitle(stringResource(R.string.requirements))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        ) {
            vacancy.requirements.requiredSkills.forEach { skill ->
                InfoChip(text = skill)
            }
        }
        vacancy.requirements.experienceYearsRequired?.let { experience ->
            Text(
                text = stringResource(R.string.experience_years, experience),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.tertiary,
            )
        }
    }
}

@Composable
internal fun DetailsLoader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .shimmer(16.dp)
            )
        }
    }
}
