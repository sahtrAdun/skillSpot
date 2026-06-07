package dot.adun.feature.authorized.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.components.textFields.core.TextFieldDefaults
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.details.vacancy.edit.VacancyEditViewIntents

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VacancyEditForm(
    titleField: TextFieldData,
    descriptionField: TextFieldData,
    skills: List<String>,
    skillInput: String,
    experienceYears: String,
    paymentMethod: PaymentType,
    budget: String,
    currency: Currency,
    loadState: LoadState,
    saveState: LoadState,
    isValid: Boolean,
    intents: VacancyEditViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.vacancy_edit_title),
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
                val descriptionRequester = remember { FocusRequester() }
                val skillRequester = remember { FocusRequester() }

                ContentLabel(
                    label = resRef(R.string.title),
                ) {
                    SimpleTextField(
                        data = titleField,
                        onValueChange = intents.changeTitle,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        actions = KeyboardActions(
                            onNext = { descriptionRequester.requestFocus() }
                        ),
                        borderVisibility = BorderVisibility.Always
                    )
                }

                ContentLabel(
                    label = resRef(R.string.vacancy_description),
                ) {
                    SimpleTextField(
                        data = descriptionField,
                        onValueChange = intents.changeDescription,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        minLines = 5,
                        maxLines = 5,
                        actions = KeyboardActions(
                            onNext = { skillRequester.requestFocus() }
                        ),
                        borderVisibility = BorderVisibility.Always,
                        modifier = Modifier.focusRequester(descriptionRequester),
                    )
                }

                ContentLabel(
                    label = resRef(R.string.skills),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            SimpleTextField(
                                data = TextFieldData(value = skillInput),
                                onValueChange = intents.changeSkillInput,
                                placeholder = stringResource(R.string.skill_input_hint),
                                enabled = !loadState.isLoading,
                                borderVisibility = BorderVisibility.Always,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(skillRequester),
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(TextFieldDefaults.height)
                                .surface(
                                    color = AppTheme.colors.layer.primaryTranslucent,
                                    shape = AppTheme.shapes.small,
                                )
                                .clickableEffect(Clickable.of(intents.addSkill))
                                .padding(AppTheme.paddings.full.small)
                        ) {
                            Text(
                                text = stringResource(R.string.add),
                                color = AppTheme.colors.text.accent,
                                style = AppTheme.typography.body1,
                            )
                        }
                    }

                    if (skills.isNotEmpty()) {
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
                                        .clickableEffect(Clickable.of { intents.removeSkill(skill) }),
                                )
                            }
                        }
                    }
                }

                ContentLabel(
                    label = resRef(R.string.vacancy_experience),
                ) {
                    SimpleTextField(
                        data = TextFieldData(value = experienceYears),
                        onValueChange = intents.changeExperience,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(
                    label = resRef(R.string.payment_method),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.space.xs),
                    ) {
                        PaymentType.entries.forEach { type ->
                            val selected = type == paymentMethod
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
                                    .clickableEffect(Clickable.of { intents.changePaymentMethod(type) }),
                            )
                        }
                    }
                }

                ContentLabel(
                    label = resRef(R.string.budget),
                ) {
                    SimpleTextField(
                        data = TextFieldData(value = budget),
                        onValueChange = intents.changeBudget,
                        enabled = !loadState.isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done,
                        ),
                        borderVisibility = BorderVisibility.Always,
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
            }

            VSpacer(padding.bottom)
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
