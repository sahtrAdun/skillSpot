package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.modifiers.shimmer
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.formatDateTime

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        modifier = modifier
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.large,
                padding = AppTheme.paddings.inset.content,
            ),
        content = content,
    )
}

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
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
fun InitialsAvatar(
    name: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
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
                style = AppTheme.typography.subhead3,
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
fun CreatorCard(
    title: String,
    creator: PublicProfile?,
    loadState: LoadState,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier) {
        SectionTitle(title)
        when {
            creator != null -> CreatorInfo(creator)
            loadState.isLoading -> CreatorShimmer()
            else -> Unit
        }
    }
}

@Composable
private fun CreatorInfo(creator: PublicProfile) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        InitialsAvatar(name = creator.fullName)
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = creator.fullName?.takeIf { it.isNotBlank() }
                    ?: stringResource(creator.role.titleRes()),
                style = AppTheme.typography.subhead2,
                color = AppTheme.colors.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            creator.roleLabelOrNull()?.let { role ->
                Text(
                    text = role,
                    style = AppTheme.typography.caption1,
                    color = AppTheme.colors.text.accent,
                )
            }
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs)) {
        InfoChip(
            text = creator.ratingAvg
                ?.let { stringResource(R.string.rating_value, formatRating(it)) }
                ?: stringResource(R.string.no_rating),
        )
        if (creator.role == UserRole.Freelancer) {
            InfoChip(
                text = stringResource(R.string.tasks_completed, creator.tasksCompleted),
                accent = false,
            )
        }
    }

    creator.locationOrNull()?.let { location ->
        Text(
            text = location,
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.text.tertiary,
        )
    }

    creator.bio?.takeIf { it.isNotBlank() }?.let { bio ->
        Text(
            text = bio,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.secondary,
        )
    }

    Text(
        text = stringResource(R.string.member_since, creator.createdAt.formatDateTime()),
        style = AppTheme.typography.caption2,
        color = AppTheme.colors.text.hint,
    )
}

@Composable
private fun CreatorShimmer() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(modifier = Modifier.size(48.dp).shimmer(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.width(160.dp).height(14.dp).shimmer(7.dp))
            Box(modifier = Modifier.width(90.dp).height(12.dp).shimmer(6.dp))
        }
    }
    VSpacer(AppTheme.paddings.space.xs)
    Box(modifier = Modifier.fillMaxWidth().height(12.dp).shimmer(6.dp))
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

private fun formatRating(rating: Float): String =
    if (rating == rating.toLong().toFloat()) rating.toLong().toString()
    else String.format("%.1f", rating)

private fun PublicProfile.locationOrNull(): String? {
    val parts = listOfNotNull(
        city?.takeIf { it.isNotBlank() },
        country?.takeIf { it.isNotBlank() },
    )
    return parts.takeIf { it.isNotEmpty() }?.joinToString(separator = ", ")
}

@Composable
private fun PublicProfile.roleLabelOrNull(): String? = when (role) {
    UserRole.Freelancer -> stringResource(R.string.role_freelancer)
    UserRole.Customer -> stringResource(R.string.role_customer)
    UserRole.None -> null
}

private fun UserRole.titleRes(): Int = when (this) {
    UserRole.Freelancer -> R.string.role_freelancer
    UserRole.Customer -> R.string.role_customer
    UserRole.None -> R.string.author
}
