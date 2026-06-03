package dot.adun.feature.authorized.ui.component

import androidx.compose.animation.animateContentSize
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
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.preview.PreviewColumn
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.domain.entity.Resume
import dot.adun.feature.authorized.ui.mappers.buildPaymentLabel
import dot.adun.feature.authorized.ui.mappers.label
import java.time.LocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResumeItem(
    resume: Resume,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var hasOverflow by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
                border = Border(
                    color = AppTheme.colors.border.primary,
                    shape = AppTheme.shapes.medium
                )
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(AppTheme.paddings.inset.content)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = resume.title,
                style = AppTheme.typography.subhead3,
                color = AppTheme.colors.text.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            HSpacer(AppTheme.paddings.space.small)
            Text(
                text = resume.activeLabel.display(),
                style = AppTheme.typography.caption2,
                color = if (resume.isActive) AppTheme.colors.layer.positive else AppTheme.colors.text.disabled,
            )
        }

        if (!resume.bio.isNullOrBlank()) {
            VSpacer(AppTheme.paddings.space.xxs)
            Text(
                text = resume.bio!!,
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
        }


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
            resume.skills.main.forEach { skill ->
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

        VSpacer(AppTheme.paddings.space.small)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = buildPaymentLabel(resume.paymentInfo),
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.text.primary,
            )
            Text(
                text = resume.availability
                    .map { it.label.display() }
                    .joinToString(", ") { it },
                style = AppTheme.typography.caption2,
                color = AppTheme.colors.text.hint,
            )
        }
    }
}

@Preview
@Composable
private fun ResumeItemPreview() {
    PreviewColumn {
        ResumeItem(
            resume = Resume(
                id = "1",
                freelancerId = "freelancer1",
                title = "Kotlin Multiplatform Developer",
                bio = "Experienced developer with 5+ years in mobile development. Passionate about KMP and Compose.",
                links = Resume.Links(
                    githubUrl = "https://github.com/example",
                    portfolioUrl = null,
                ),
                skills = Resume.Skills(
                    main = listOf("Kotlin", "Compose", "KMP", "Coroutines"),
                    secondary = listOf("Swift", "Python"),
                ),
                paymentInfo = Resume.PaymentInfo(
                    preference = PaymentType.Hourly,
                    minRate = 40.0,
                    currency = Currency.USD,
                ),
                availability = listOf(AvailabilityType.FullTime, AvailabilityType.PartTime),
                isActive = true,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
            onClick = {}
        )
    }
}
