package dot.adun.feature.profile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.VerticalList
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.ScaffoldPaddings
import dot.adun.core.ui.components.buttons.IcButton
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.PublicProfile
import dot.adun.feature.profile.domain.entity.Review
import dot.adun.feature.profile.ui.R
import dot.adun.feature.profile.ui.mappers.formatDate
import dot.adun.feature.profile.ui.mappers.formatRating
import dot.adun.feature.profile.ui.mappers.label
import dot.adun.feature.profile.ui.screen.ProfileTab
import dot.adun.feature.profile.ui.screen.ProfileViewIntents

@Composable
fun ProfileLayout(
    profile: PublicProfile?,
    reviews: List<Review>,
    content: ProfileContent,
    selectedTab: ProfileTab,
    isOwnProfile: Boolean,
    reviewsLoadState: LoadState,
    contentLoadState: LoadState,
    intents: ProfileViewIntents,
) {
    AdunScaffold(
        contentBackground = AppTheme.colors.layer.surface,
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.profile_title),
                onBackClick = intents.navigateBack,
                trailingContent = if (isOwnProfile) {
                    {
                        IcButton(
                            vector = Icons.AutoMirrored.Filled.Logout,
                            colors = AppTheme.presets.buttons.icon.transparent,
                            onClick = intents.logout,
                            iconModifier = Modifier.requiredSize(16.dp),
                            modifier = Modifier
                                .surface(
                                    color = AppTheme.colors.layer.surface,
                                    shape = CircleShape,
                                    padding = 4.dp
                                ),
                        )
                    }
                } else null,
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileHeader(
                profile = profile,
                topPadding = padding.top,
                isOwnProfile = isOwnProfile,
                onEditClick = intents.editProfile
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(AppTheme.colors.layer.background)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(AppTheme.colors.layer.surface),
            ) {
                ProfileTabRow(
                    tabs = profileTabs(profile?.role),
                    selected = selectedTab,
                    onSelect = intents.selectTab,
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        ProfileTab.Info -> InfoTab(profile, padding)
                        ProfileTab.Reviews -> ReviewsTab(
                            reviews = reviews,
                            loadState = reviewsLoadState,
                            bottomPadding = padding.bottom,
                            onSenderClick = intents.openProfile,
                        )

                        ProfileTab.Content -> ContentTab(
                            content = content,
                            loadState = contentLoadState,
                            bottomPadding = padding.bottom,
                            onOpenDetails = { isVacancy, id ->
                                intents.openDetails(isVacancy to id)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    profile: PublicProfile?,
    topPadding: Dp,
    isOwnProfile: Boolean,
    onEditClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        AppTheme.colors.layer.primaryTranslucent,
                        AppTheme.colors.layer.background,
                    )
                )
            )
            .padding(top = topPadding + 16.dp, bottom = 28.dp)
            .padding(horizontal = 16.dp),
    ) {
        ProfileAvatar(name = profile?.fullName)
        VSpacer(12.dp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = profile?.fullName?.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.profile_unnamed),
                style = AppTheme.typography.subhead1,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Center,
            )
            if (isOwnProfile) {
                IcButton(
                    vector = Icons.Default.Edit,
                    colors = AppTheme.presets.buttons.icon.transparent,
                    onClick = onEditClick,
                    iconModifier = Modifier.requiredSize(16.dp),
                    modifier = Modifier
                        .surface(
                            color = AppTheme.colors.layer.surface,
                            shape = CircleShape,
                            padding = 4.dp
                        ),
                )
            }
        }
        profile?.role?.label()?.let { role ->
            VSpacer(4.dp)
            Text(
                text = role,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.accent,
            )
        }
    }
}

@Composable
private fun InfoTab(
    profile: PublicProfile?,
    padding: ScaffoldPaddings,
) {
    profile ?: return
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs)) {
            SectionLabel(stringResource(R.string.profile_about))
            Text(
                text = profile.bio?.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.profile_no_bio),
                style = AppTheme.typography.body2,
                color = if (profile.bio.isNullOrBlank()) AppTheme.colors.text.hint
                else AppTheme.colors.text.secondary,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs)) {
            SectionLabel(stringResource(R.string.profile_details))
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
                modifier = Modifier
                    .fillMaxWidth()
                    .surface(
                        color = AppTheme.colors.layer.onSurface,
                        shape = AppTheme.shapes.medium,
                        padding = AppTheme.paddings.inset.content,
                    ),
            ) {
                profile.role.label()?.let { role ->
                    AttributeRow(stringResource(R.string.profile_role), role)
                }

                AttributeRow(
                    label = stringResource(R.string.profile_rating),
                    value = profile.ratingAvg
                        ?.let { stringResource(R.string.profile_rating_value, formatRating(it)) }
                        ?: stringResource(R.string.profile_no_rating),
                    valueColor = if (profile.ratingAvg != null) AppTheme.colors.text.accent
                    else AppTheme.colors.text.hint,
                )

                if (profile.role == UserRole.Freelancer) {
                    AttributeRow(
                        label = stringResource(R.string.profile_tasks_label),
                        value = profile.tasksCompleted.toString(),
                    )
                }

                profile.age?.let { age ->
                    AttributeRow(
                        label = stringResource(R.string.profile_age),
                        value = stringResource(R.string.profile_age_value, age.toInt()),
                    )
                }

                profileLocation(profile)?.let { location ->
                    AttributeRow(stringResource(R.string.profile_location), location)
                }

                AttributeRow(
                    label = stringResource(R.string.profile_member_since_label),
                    value = profile.createdAt.formatDate(),
                )
            }
        }

        VSpacer(padding.bottom)
    }
}

@Composable
private fun AttributeRow(
    label: String,
    value: String,
    valueColor: Color = AppTheme.colors.text.primary,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.secondary,
        )
        Text(
            text = value,
            style = AppTheme.typography.body2,
            color = valueColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ReviewsTab(
    reviews: List<Review>,
    loadState: LoadState,
    bottomPadding: Dp,
    onSenderClick: (String) -> Unit,
) {
    when {
        reviews.isEmpty() && loadState.isLoading -> CenteredLoader()
        reviews.isEmpty() -> EmptyState(stringResource(R.string.profile_no_reviews))
        else -> VerticalList(modifier = Modifier.fillMaxSize()) {
            items(reviews.size) { index ->
                val review = reviews[index]
                ReviewItem(
                    review = review,
                    onSenderClick = { onSenderClick(review.senderId) },
                )
            }
            item { VSpacer(bottomPadding) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContentTab(
    content: ProfileContent,
    loadState: LoadState,
    bottomPadding: Dp,
    onOpenDetails: (isVacancy: Boolean, id: String) -> Unit,
) {
    when (content) {
        is ProfileContent.Vacancies -> VerticalList(modifier = Modifier.fillMaxSize()) {
            items(content.items.size) { index ->
                val vacancy = content.items[index]
                ProfileVacancyCard(
                    vacancy = vacancy,
                    onClick = { onOpenDetails(true, vacancy.id) },
                )
            }
            item { VSpacer(bottomPadding) }
        }

        is ProfileContent.Resumes -> VerticalList(modifier = Modifier.fillMaxSize()) {
            items(content.items.size) { index ->
                val resume = content.items[index]
                ProfileResumeCard(
                    resume = resume,
                    onClick = { onOpenDetails(false, resume.id) },
                )
            }
            item { VSpacer(bottomPadding) }
        }

        ProfileContent.Empty -> if (loadState.isLoading) {
            CenteredLoader()
        } else {
            EmptyState(stringResource(R.string.profile_no_content))
        }
    }
}

@Composable
private fun CenteredLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Loader(appearance = LoaderAppearance.Solid(AppTheme.colors.layer.primary))
    }
}

@Composable
private fun EmptyState(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.hint,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun profileTabs(role: UserRole?): List<Pair<ProfileTab, String>> {
    val contentLabel = when (role) {
        UserRole.Freelancer -> stringResource(R.string.profile_tab_resumes)
        else -> stringResource(R.string.profile_tab_vacancies)
    }
    return listOf(
        ProfileTab.Info to stringResource(R.string.profile_tab_info),
        ProfileTab.Reviews to stringResource(R.string.profile_tab_reviews),
        ProfileTab.Content to contentLabel,
    )
}

private fun profileLocation(profile: PublicProfile): String? {
    val parts = listOfNotNull(
        profile.city?.takeIf { it.isNotBlank() },
        profile.country?.takeIf { it.isNotBlank() },
    )
    return parts.takeIf { it.isNotEmpty() }?.joinToString(separator = ", ")
}
