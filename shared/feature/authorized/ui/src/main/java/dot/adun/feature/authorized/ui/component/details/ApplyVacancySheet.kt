package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.loaders.Loader
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.Border
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.profile.domain.entity.ProfileResume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyVacancySheet(
    resumes: List<ProfileResume>,
    resumesLoadState: LoadState,
    applyState: LoadState,
    onApply: (resumeId: String, coverLetter: String?) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedResumeId by remember { mutableStateOf<String?>(null) }
    var coverLetter by remember { mutableStateOf("") }

    LaunchedEffect(resumes) {
        if (selectedResumeId == null) {
            selectedResumeId = resumes.firstOrNull()?.id
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppTheme.colors.layer.surface,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.regular),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
                .navigationBarsPadding()
        ) {
            Text(
                text = stringResource(R.string.apply_title),
                style = AppTheme.typography.subhead2,
                color = AppTheme.colors.text.primary,
            )

            when {
                resumes.isEmpty() && resumesLoadState.isLoading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                    ) {
                        Loader(appearance = LoaderAppearance.Solid(AppTheme.colors.layer.primary))
                    }
                }

                resumes.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.apply_no_resumes),
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.text.hint,
                    )
                }

                else -> {
                    Text(
                        text = stringResource(R.string.apply_select_resume),
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.text.secondary,
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 280.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        resumes.forEach { resume ->
                            ResumeOption(
                                title = resume.title,
                                selected = resume.id == selectedResumeId,
                                onClick = { selectedResumeId = resume.id },
                            )
                        }
                    }

                    SimpleTextField(
                        data = TextFieldData(value = coverLetter),
                        onValueChange = { coverLetter = it },
                        placeholder = stringResource(R.string.apply_cover_letter_hint),
                        borderVisibility = BorderVisibility.Always,
                    )

                    PrimaryButton(
                        state = rememberButtonState(
                            loading = applyState.isLoading,
                            disabled = selectedResumeId == null,
                        ),
                        clickable = Clickable.of {
                            selectedResumeId?.let { id ->
                                onApply(id, coverLetter.ifBlank { null })
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = stringResource(R.string.apply_send))
                    }
                }
            }
        }
    }
}

@Composable
private fun ResumeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .surface(
                color = if (selected) AppTheme.colors.layer.primaryTranslucent
                else AppTheme.colors.layer.onSurface,
                shape = AppTheme.shapes.medium,
                padding = AppTheme.paddings.inset.content,
                border = if (selected) {
                    Border(color = AppTheme.colors.border.onPrimary, shape = AppTheme.shapes.medium)
                } else null,
            )
            .clickableEffect(Clickable.of(onClick)),
    ) {
        Text(
            text = title,
            style = AppTheme.typography.body1,
            color = if (selected) AppTheme.colors.text.accent else AppTheme.colors.text.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        if (selected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = AppTheme.colors.icon.accent,
            )
        }
    }
}
