package dot.adun.feature.authorized.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dot.adun.common.resources.Res
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.preview.PreviewColumn
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.domain.entity.Vacancy
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.buildPaymentLabel
import dot.adun.feature.authorized.ui.mappers.label
import java.time.LocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VacancyItem(
    vacancy: Vacancy,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var hasOverflow by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(AppTheme.paddings.inset.content)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = vacancy.info.title,
                style = AppTheme.typography.subhead3,
                color = AppTheme.colors.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            HSpacer(AppTheme.paddings.space.small)
            Text(
                text = vacancy.status.label.display(),
                style = AppTheme.typography.caption2,
                color = when (vacancy.status) {
                    Vacancy.Status.Open -> AppTheme.colors.layer.positive
                    Vacancy.Status.InProgress -> AppTheme.colors.text.warning
                    Vacancy.Status.Closed -> AppTheme.colors.text.disabled
                }
            )
        }

        VSpacer(AppTheme.paddings.space.xxs)

        Text(
            text = vacancy.info.description,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.secondary,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = {
                hasOverflow = when {
                    it.hasVisualOverflow && !expanded ||
                            !it.hasVisualOverflow && expanded -> true
                    else -> it.hasVisualOverflow
                }
            },
            modifier = Modifier
                .padding(vertical = 6.dp)
                .animateContentSize()
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (hasOverflow) {
                Text(
                    text = stringResource(
                        id = if (expanded) Res.strings.collapse else Res.strings.view_all
                    ),
                    color = AppTheme.colors.text.accent,
                    style = AppTheme.typography.caption1,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .clickableEffect(Clickable.of { expanded = !expanded })
                )
            }
        }

        VSpacer(AppTheme.paddings.space.small)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        ) {
            vacancy.requirements.requiredSkills.forEach { skill ->
                Text(
                    text = skill,
                    style = AppTheme.typography.caption2,
                    color = AppTheme.colors.text.accent,
                    modifier = Modifier.surface(
                        color = AppTheme.colors.layer.primaryTranslucent,
                        shape = AppTheme.shapes.small,
                        padding = AppTheme.paddings.full.xs,
                    )
                )
            }
        }

        vacancy.requirements.experienceYearsRequired?.let { experience ->
            VSpacer(AppTheme.paddings.space.xxs)
            Text(
                text = stringResource(R.string.required_exp, experience),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.tertiary,
            )
        }

        VSpacer(AppTheme.paddings.space.small)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = buildPaymentLabel(vacancy.payment),
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.text.primary,
            )
            Text(
                text = stringResource(R.string.views, vacancy.viewsCount),
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.hint,
            )
        }
    }
}

@Preview
@Composable
private fun VacancyItemPreview() {
    PreviewColumn {
        VacancyItem(
            vacancy = Vacancy(
                id = "1",
                clientId = "client1",
                info = Vacancy.Info(
                    title = "Senior Android Developer",
                    description = "We are looking for an experienced Android developer to join our team and build beautiful, performant applications.",
                ),
                requirements = Vacancy.Requirements(
                    requiredSkills = listOf("Kotlin", "Compose", "MVVM"),
                    experienceYearsRequired = 3,
                ),
                payment = Vacancy.Payment(
                    method = PaymentType.Hourly,
                    budget = 50.0,
                    currency = Currency.USD,
                ),
                status = Vacancy.Status.Open,
                viewsCount = 128,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
            onClick = {}
        )
    }
}
