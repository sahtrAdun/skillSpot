package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.ContentLabel
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.details.resume.edit.ResumeEditViewIntents

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResumeEditForm(
    titleField: TextFieldData,
    bioField: TextFieldData,
    mainSkills: List<String>,
    mainSkillInput: String,
    secondarySkills: List<String>,
    secondarySkillInput: String,
    paymentPreference: PaymentType,
    minRate: String,
    currency: Currency,
    availability: List<AvailabilityType>,
    githubUrl: String,
    portfolioUrl: String,
    isActive: Boolean,
    loadState: LoadState,
    saveState: LoadState,
    isValid: Boolean,
    intents: ResumeEditViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.resume_edit_title),
                onBackClick = intents.navigateBack,
            )
        },
        bottomContent = {
            PrimaryButton(
                state = rememberButtonState(
                    loading = saveState.isLoading,
                    disabled = loadState.isLoading
                ),
                clickable = Clickable.of(intents.save),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
        )

        Column(
            modifier = Modifier
                .padding(top = padding.top)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            VSpacer(12.dp)

            FormSection(
                modifier = Modifier.fillMaxWidth()
            ) {
                val bioRequester = remember { FocusRequester() }
                val mainSkillRequester = remember { FocusRequester() }

                ContentLabel(
                    label = resRef(R.string.title),
                ) {
                    SimpleTextField(
                        data = titleField,
                        onValueChange = intents.changeTitle,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        actions = KeyboardActions(
                            onNext = { bioRequester.requestFocus() }
                        ),
                        borderVisibility = BorderVisibility.Always
                    )
                }

                ContentLabel(
                    label = resRef(R.string.about),
                ) {
                    SimpleTextField(
                        data = bioField,
                        onValueChange = intents.changeBio,
                        enabled = !loadState.isLoading,
                        modifier = Modifier.focusRequester(bioRequester),
                        borderVisibility = BorderVisibility.Always
                    )
                }

                ContentLabel(
                    label = resRef(R.string.main_skills),
                ) {
                    SkillInputSection(
                        input = mainSkillInput,
                        skills = mainSkills,
                        onInputChange = intents.changeMainSkillInput,
                        onAdd = intents.addMainSkill,
                        onRemove = intents.removeMainSkill,
                        enabled = !loadState.isLoading,
                        focusRequester = mainSkillRequester,
                    )
                }

                ContentLabel(
                    label = resRef(R.string.secondary_skills),
                ) {
                    SkillInputSection(
                        input = secondarySkillInput,
                        skills = secondarySkills,
                        onInputChange = intents.changeSecondarySkillInput,
                        onAdd = intents.addSecondarySkill,
                        onRemove = intents.removeSecondarySkill,
                        enabled = !loadState.isLoading,
                    )
                }

                ContentLabel(
                    label = resRef(R.string.payment_method),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                    ) {
                        PaymentType.entries.forEach { type ->
                            val selected = type == paymentPreference
                            Text(
                                text = type.label.display(),
                                style = AppTheme.typography.caption1,
                                color = if (selected) AppTheme.colors.text.accent
                                    else AppTheme.colors.text.primary,
                                modifier = Modifier
                                    .surface(
                                        color = if (selected) AppTheme.colors.layer.primaryTranslucent
                                            else AppTheme.colors.layer.surface,
                                        shape = AppTheme.shapes.small,
                                        padding = AppTheme.paddings.full.small,
                                    )
                                    .clickableEffect(Clickable.of { intents.changePaymentPreference(type) }),
                            )
                        }
                    }
                }

                ContentLabel(
                    label = resRef(R.string.min_rate),
                ) {
                    SimpleTextField(
                        data = TextFieldData(value = minRate),
                        onValueChange = intents.changeMinRate,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                        ),
                    )
                }

                ContentLabel(
                    label = resRef(R.string.currency),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                    ) {
                        Currency.entries.forEach { curr ->
                            val selected = curr == currency
                            Text(
                                text = curr.name,
                                style = AppTheme.typography.caption1,
                                color = if (selected) AppTheme.colors.text.accent
                                    else AppTheme.colors.text.primary,
                                modifier = Modifier
                                    .surface(
                                        color = if (selected) AppTheme.colors.layer.primaryTranslucent
                                            else AppTheme.colors.layer.surface,
                                        shape = AppTheme.shapes.small,
                                        padding = AppTheme.paddings.full.small,
                                    )
                                    .clickableEffect(Clickable.of { intents.changeCurrency(curr) }),
                            )
                        }
                    }
                }

                ContentLabel(
                    label = resRef(R.string.availability),
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                    ) {
                        AvailabilityType.entries.forEach { type ->
                            val selected = type in availability
                            Text(
                                text = type.label.display(),
                                style = AppTheme.typography.caption1,
                                color = if (selected) AppTheme.colors.text.accent
                                    else AppTheme.colors.text.primary,
                                modifier = Modifier
                                    .surface(
                                        color = if (selected) AppTheme.colors.layer.primaryTranslucent
                                            else AppTheme.colors.layer.surface,
                                        shape = AppTheme.shapes.small,
                                        padding = AppTheme.paddings.full.small,
                                    )
                                    .clickableEffect(Clickable.of { intents.toggleAvailability(type) }),
                            )
                        }
                    }
                }

                ContentLabel(
                    label = resRef(R.string.github),
                ) {
                    SimpleTextField(
                        data = TextFieldData(value = githubUrl),
                        onValueChange = intents.changeGithubUrl,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next,
                        ),
                    )
                }

                ContentLabel(
                    label = resRef(R.string.portfolio),
                ) {
                    SimpleTextField(
                        data = TextFieldData(value = portfolioUrl),
                        onValueChange = intents.changePortfolioUrl,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Done,
                        ),
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.resume_active_status),
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.text.primary,
                    )
                    Text(
                        text = if (isActive) stringResource(R.string.yes)
                            else stringResource(R.string.no),
                        style = AppTheme.typography.caption1,
                        color = if (isActive) AppTheme.colors.layer.positive
                            else AppTheme.colors.text.disabled,
                        modifier = Modifier
                            .surface(
                                color = if (isActive) AppTheme.colors.layer.primaryTranslucent
                                    else AppTheme.colors.layer.onSurface,
                                shape = AppTheme.shapes.small,
                                padding = AppTheme.paddings.full.small,
                            )
                            .clickableEffect(Clickable.of(intents.toggleActive)),
                    )
                }
            }

            VSpacer(padding.bottom)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillInputSection(
    input: String,
    skills: List<String>,
    onInputChange: (String) -> Unit,
    onAdd: () -> Unit,
    onRemove: (String) -> Unit,
    enabled: Boolean,
    focusRequester: FocusRequester? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        modifier = Modifier.fillMaxWidth()
    ) {
        val modifier = if (focusRequester != null) {
            Modifier.weight(1f).focusRequester(focusRequester)
        } else {
            Modifier.weight(1f)
        }

        SimpleTextField(
            data = TextFieldData(value = input),
            onValueChange = onInputChange,
            placeholder = stringResource(R.string.skill_input_hint),
            enabled = enabled,
            borderVisibility = BorderVisibility.Always,
            colorPreset = AppTheme.presets.textFields.accent,
            modifier = modifier,
        )
        Text(
            text = stringResource(R.string.add),
            color = AppTheme.colors.text.accent,
            style = AppTheme.typography.body1,
            modifier = Modifier
                .surface(
                    color = AppTheme.colors.layer.primaryTranslucent,
                    shape = AppTheme.shapes.small,
                    padding = AppTheme.paddings.full.small,
                )
                .clickableEffect(Clickable.of(onAdd))
                .padding(horizontal = 12.dp, vertical = 12.dp),
        )
    }

    if (skills.isNotEmpty()) {
        VSpacer(AppTheme.paddings.space.xs)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
        ) {
            skills.forEach { skill ->
                Text(
                    text = "$skill x",
                    style = AppTheme.typography.caption2,
                    color = AppTheme.colors.text.accent,
                    modifier = Modifier
                        .surface(
                            color = AppTheme.colors.layer.primaryTranslucent,
                            shape = AppTheme.shapes.small,
                            padding = AppTheme.paddings.full.xs,
                        )
                        .clickableEffect(Clickable.of { onRemove(skill) }),
                )
            }
        }
    }
}

@Composable
private fun FormSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .surface(
                color = AppTheme.colors.layer.surface,
                shape = AppTheme.shapes.large,
                padding = AppTheme.paddings.inset.content,
            )
    ) {
        content()
    }
}
