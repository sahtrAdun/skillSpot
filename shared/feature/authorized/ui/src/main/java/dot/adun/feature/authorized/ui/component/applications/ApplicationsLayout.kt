package dot.adun.feature.authorized.ui.component.applications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.LoadState
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.HSpacer
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.VerticalList
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.IncomingApplication
import dot.adun.feature.authorized.domain.entity.MyApplication
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.component.AttachmentBox
import dot.adun.feature.authorized.ui.component.details.InfoChip
import dot.adun.feature.authorized.ui.component.details.InitialsAvatar
import dot.adun.feature.authorized.ui.mappers.buildAmount
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.applications.ApplicationsViewIntents
import dot.adun.core.domain.entity.LoadState as DomainLoadState

@Composable
fun ApplicationsLayout(
    userRole: UserRole,
    myApplications: List<MyApplication>,
    incomingApplications: List<IncomingApplication>,
    loadState: DomainLoadState,
    approvingId: String?,
    intents: ApplicationsViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.applications_title),
                onBackClick = intents.navigateBack,
            )
        }
    ) { padding ->
        LoadState(
            loadState = loadState,
            modifier = Modifier.padding(top = padding.top),
        ) {
            when (userRole) {
                UserRole.Freelancer -> FreelancerApplications(
                    applications = myApplications,
                    bottomPadding = padding.bottom,
                    onOpenDetails = { id -> intents.openDetails(true to id) },
                    onDecline = intents.decline,
                )

                UserRole.Customer -> ClientApplications(
                    applications = incomingApplications,
                    bottomPadding = padding.bottom,
                    approvingId = approvingId,
                    onOpenDetails = { id -> intents.openDetails(false to id) },
                    onApprove = intents.approve,
                )

                UserRole.None -> Unit
            }
        }
    }
}

@Composable
private fun FreelancerApplications(
    applications: List<MyApplication>,
    bottomPadding: androidx.compose.ui.unit.Dp,
    onOpenDetails: (String) -> Unit,
    onDecline: (String) -> Unit,
) {
    if (applications.isEmpty()) {
        EmptyState()
        return
    }
    VerticalList(modifier = Modifier.fillMaxSize()) {
        items(applications.size) { index ->
            val application = applications[index]
            AttachmentBox(
                modifier = Modifier.fillMaxWidth(),
                item = {
                    MyApplicationItem(
                        application = application,
                        onClick = { onOpenDetails(application.vacancyId) },
                    )
                },
                attachment = {
                    AttachmentAction(
                        text = stringResource(R.string.application_decline),
                        color = AppTheme.colors.text.error,
                        onClick = { onDecline(application.applicationId) },
                    )
                },
            )
        }
        item { VSpacer(bottomPadding) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ClientApplications(
    applications: List<IncomingApplication>,
    bottomPadding: androidx.compose.ui.unit.Dp,
    approvingId: String?,
    onOpenDetails: (String) -> Unit,
    onApprove: (String) -> Unit,
) {
    if (applications.isEmpty()) {
        EmptyState()
        return
    }
    VerticalList(modifier = Modifier.fillMaxSize()) {
        items(applications.size) { index ->
            val application = applications[index]
            AttachmentBox(
                modifier = Modifier.fillMaxWidth(),
                item = {
                    IncomingApplicationItem(
                        application = application,
                        onClick = { onOpenDetails(application.resumeId) },
                    )
                },
                attachment = {
                    Box(modifier = Modifier.padding(AppTheme.paddings.inset.content)) {
                        PrimaryButton(
                            state = rememberButtonState(
                                loading = approvingId == application.applicationId,
                            ),
                            clickable = Clickable.of { onApprove(application.applicationId) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = stringResource(R.string.application_approve))
                        }
                    }
                },
            )
        }
        item { VSpacer(bottomPadding) }
    }
}

@Composable
private fun MyApplicationItem(
    application: MyApplication,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = Modifier
            .fillMaxWidth()
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.medium,
            )
            .clickableEffect(Clickable.of(onClick))
            .padding(AppTheme.paddings.inset.content),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = application.vacancyTitle,
                style = AppTheme.typography.subhead3,
                color = AppTheme.colors.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            HSpacer(AppTheme.paddings.space.small)
            InfoChip(text = application.status.label.display(), accent = false)
        }

        Text(
            text = buildAmount(application.vacancyCurrency, application.vacancyBudget),
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.text.primary,
        )

        application.coverLetter?.takeIf { it.isNotBlank() }?.let { letter ->
            Text(
                text = letter,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IncomingApplicationItem(
    application: IncomingApplication,
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            InitialsAvatar(name = application.freelancerName, size = 40.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = application.freelancerName?.takeIf { it.isNotBlank() }
                        ?: application.resumeTitle,
                    style = AppTheme.typography.subhead3,
                    color = AppTheme.colors.text.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = application.resumeTitle,
                    style = AppTheme.typography.caption1,
                    color = AppTheme.colors.text.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            InfoChip(text = application.status.label.display(), accent = false)
        }

        if (application.resumeSkills.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            ) {
                application.resumeSkills.forEach { skill -> InfoChip(text = skill) }
            }
        }

        application.coverLetter?.takeIf { it.isNotBlank() }?.let { letter ->
            Text(
                text = letter,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.text.secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun AttachmentAction(
    text: String,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickableEffect(Clickable.of(onClick))
            .padding(vertical = 14.dp),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body1,
            color = color,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text = stringResource(R.string.applications_empty),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text.hint,
            textAlign = TextAlign.Center,
        )
    }
}
