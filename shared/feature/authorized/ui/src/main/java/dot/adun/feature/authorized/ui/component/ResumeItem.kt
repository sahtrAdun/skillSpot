package dot.adun.feature.authorized.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.WSpacer
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

private val itemHeight = 180.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResumeItem(
    resume: Resume,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(itemHeight)
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
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

        WSpacer()

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            maxLines = 2,
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
