package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import dot.adun.core.ui.components.divider.HDivider
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.buildPaymentLabel
import dot.adun.feature.authorized.ui.mappers.formatDateTime
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.details.resume.ResumeDetailsViewIntents

@Composable
fun ResumeDetailsContent(
    resume: Resume?,
    creator: PublicProfile?,
    loadState: LoadState,
    creatorLoadState: LoadState,
    canEdit: Boolean,
    intents: ResumeDetailsViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.resume_details_title),
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
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
            alignment = Alignment.BottomCenter,
        )

        LoadState(
            loadState = loadState,
            modifier = Modifier.padding(top = padding.top),
            onLoading = { DetailsLoader() }
        ) {
            resume?.let { resume ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                ) {
                    ResumeTitleCard(resume)
                    ResumeAboutCard(resume)
                    if (!canEdit) {
                        CreatorCard(
                            title = stringResource(R.string.resume_author),
                            creator = creator,
                            loadState = creatorLoadState,
                        )
                    }
                    ResumeAvailabilityCard(resume)
                    ResumeLinksCard(resume)
                    ResumeMetaCard(resume)
                    VSpacer(padding.bottom)
                }
            }
        }
    }
}

@Composable
private fun ResumeTitleCard(resume: Resume) {
    DetailCard {
        Text(
            text = resume.title,
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
                text = resume.activeLabel.display(),
                style = AppTheme.typography.body2,
                color = if (resume.isActive) AppTheme.colors.layer.positive
                else AppTheme.colors.text.disabled,
            )
        }

        Text(
            text = buildPaymentLabel(resume.paymentInfo),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.primary,
        )
    }
}

@Composable
private fun ResumeMetaCard(resume: Resume) {
    DetailCard {
        Text(
            text = stringResource(R.string.created_at, resume.createdAt.formatDateTime()),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.hint,
        )
        Text(
            text = stringResource(R.string.updated_at, resume.updatedAt.formatDateTime()),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.hint,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResumeAboutCard(resume: Resume) {
    DetailCard {
        resume.bio?.takeIf { it.isNotBlank() }?.let { bio ->
            SectionTitle(stringResource(R.string.about))
            Text(
                text = bio,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.secondary,
            )
            HDivider()
        }

        SectionTitle(stringResource(R.string.main_skills))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        ) {
            resume.skills.main.forEach { skill ->
                InfoChip(text = skill)
            }
        }

        resume.skills.secondary?.takeIf { it.isNotEmpty() }?.let { secondary ->
            SectionTitle(stringResource(R.string.secondary_skills))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            ) {
                secondary.forEach { skill ->
                    InfoChip(text = skill, accent = false)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResumeAvailabilityCard(resume: Resume) {
    DetailCard {
        SectionTitle(stringResource(R.string.availability))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        ) {
            resume.availability.forEach { type ->
                InfoChip(text = type.label.display(), accent = false)
            }
        }
    }
}

@Composable
private fun ResumeLinksCard(resume: Resume) {
    DetailCard {
        SectionTitle(stringResource(R.string.links))

        val github = resume.links.githubUrl?.takeIf { it.isNotBlank() }
        val portfolio = resume.links.portfolioUrl?.takeIf { it.isNotBlank() }

        github?.let { url ->
            Text(
                text = stringResource(R.string.github) + ": $url",
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.accent,
            )
        }
        portfolio?.let { url ->
            Text(
                text = stringResource(R.string.portfolio) + ": $url",
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.accent,
            )
        }
        if (github == null && portfolio == null) {
            Text(
                text = stringResource(R.string.no_links),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.hint,
            )
        }
    }
}
