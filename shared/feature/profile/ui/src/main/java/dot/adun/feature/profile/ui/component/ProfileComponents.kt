package dot.adun.feature.profile.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.click
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.profile.domain.entity.ProfileResume
import dot.adun.feature.profile.domain.entity.ProfileVacancy
import dot.adun.feature.profile.domain.entity.Review
import dot.adun.feature.profile.ui.R
import dot.adun.feature.profile.ui.mappers.formatAmount
import dot.adun.feature.profile.ui.mappers.formatDate
import dot.adun.feature.profile.ui.mappers.isHourly
import dot.adun.feature.profile.ui.screen.ProfileTab
import androidx.compose.ui.res.stringResource

@Composable
fun ProfileAvatar(
    name: String?,
    modifier: Modifier = Modifier,
    size: Dp = 88.dp,
) {
    val initials = name.toInitials()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .surface(
                color = AppTheme.colors.layer.primaryTranslucent,
                shape = CircleShape,
            ),
    ) {
        if (initials != null) {
            Text(
                text = initials,
                style = AppTheme.typography.subhead1,
                color = AppTheme.colors.text.accent,
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = AppTheme.colors.text.accent,
                modifier = Modifier.size(size),
            )
        }
    }
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = AppTheme.typography.subhead3,
        color = AppTheme.colors.text.primary,
        modifier = modifier,
    )
}

@Composable
fun InfoChip(
    text: String,
    modifier: Modifier = Modifier,
    accent: Boolean = true,
) {
    Text(
        text = text,
        style = AppTheme.typography.caption2,
        color = if (accent) AppTheme.colors.text.accent else AppTheme.colors.text.primary,
        modifier = modifier.surface(
            color = if (accent) AppTheme.colors.layer.primaryTranslucent
            else AppTheme.colors.layer.onSurface,
            shape = AppTheme.shapes.small,
            padding = AppTheme.paddings.full.xs,
        ),
    )
}

@Composable
fun ProfileTabRow(
    tabs: List<Pair<ProfileTab, String>>,
    selected: ProfileTab,
    onSelect: (ProfileTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        tabs.forEach { (tab, title) ->
            val isSelected = tab == selected
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .click(Clickable.of { onSelect(tab) })
                    .padding(vertical = 12.dp),
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.body2,
                    color = if (isSelected) AppTheme.colors.text.accent
                    else AppTheme.colors.text.secondary,
                )
                VSpacer(8.dp)
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 2.dp)
                        .surface(
                            color = if (isSelected) AppTheme.colors.layer.primary
                            else AppTheme.colors.layer.surface,
                            shape = AppTheme.shapes.small,
                        ),
                )
            }
        }
    }
}

@Composable
fun RatingStars(rating: Int, modifier: Modifier = Modifier) {
    val filled = rating.coerceIn(0, 5)
    Text(
        text = "★".repeat(filled) + "☆".repeat(5 - filled),
        style = AppTheme.typography.body2,
        color = AppTheme.colors.text.accent,
        modifier = modifier,
    )
}

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
    onSenderClick: (() -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = modifier
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.onSurface,
                shape = AppTheme.shapes.medium,
                padding = AppTheme.paddings.inset.content,
            ),
    ) {
        val senderModifier = if (onSenderClick != null) {
            Modifier.clickableEffect(Clickable.of(onSenderClick))
        } else {
            Modifier
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = senderModifier,
        ) {
            ProfileAvatar(name = review.senderName, size = 36.dp)
            HSpacer(AppTheme.paddings.space.small)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.senderName?.takeIf { it.isNotBlank() }
                        ?: stringResource(R.string.profile_unnamed),
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = review.createdAt.formatDate(),
                    style = AppTheme.typography.caption2,
                    color = AppTheme.colors.text.hint,
                )
            }
            RatingStars(rating = review.rating)
        }

        review.comment?.takeIf { it.isNotBlank() }?.let { comment ->
            Text(
                text = comment,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.secondary,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileVacancyCard(
    vacancy: ProfileVacancy,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ContentCard(
        title = vacancy.title,
        skills = vacancy.skills,
        payment = paymentLabel(vacancy.currency, vacancy.budget, vacancy.paymentMethod),
        modifier = modifier,
        onClick = onClick,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileResumeCard(
    resume: ProfileResume,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ContentCard(
        title = resume.title,
        skills = resume.skills,
        payment = stringResource(
            R.string.profile_from,
            paymentLabel(resume.currency, resume.minRate, resume.paymentPreference),
        ),
        modifier = modifier,
        onClick = onClick,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContentCard(
    title: String,
    skills: List<String>,
    payment: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val clickModifier = if (onClick != null) {
        Modifier.clickableEffect(Clickable.of(onClick))
    } else {
        Modifier
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        modifier = modifier
            .fillMaxWidth()
            .then(clickModifier)
            .surface(
                color = AppTheme.colors.layer.onSurface,
                shape = AppTheme.shapes.medium,
                padding = AppTheme.paddings.inset.content,
            ),
    ) {
        Text(
            text = title,
            style = AppTheme.typography.subhead3,
            color = AppTheme.colors.text.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        if (skills.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            ) {
                skills.forEach { skill -> InfoChip(text = skill) }
            }
        }
        Text(
            text = payment,
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.text.primary,
        )
    }
}

@Composable
private fun paymentLabel(currency: String, amount: Double, method: String): String {
    val formatted = formatAmount(currency, amount)
    return if (isHourly(method)) stringResource(R.string.profile_per_hour, formatted) else formatted
}

private fun String?.toInitials(): String? {
    if (this.isNullOrBlank()) return null
    return trim()
        .split(Regex("\\s+"))
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString(separator = "")
        .ifBlank { null }
}
