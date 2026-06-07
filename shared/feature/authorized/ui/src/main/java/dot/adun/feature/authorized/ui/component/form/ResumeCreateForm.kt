package dot.adun.feature.authorized.ui.component.form

import androidx.compose.foundation.layout.Column
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
import dot.adun.core.ui.theme.AppTheme
import dot.adun.core.ui.util.display
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.authorized.ui.mappers.label
import dot.adun.feature.authorized.ui.screen.create.resume.CreateResumeViewIntents

@Composable
fun ResumeCreateForm(
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
    saveState: LoadState,
    intents: CreateResumeViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.resume_create_title),
                onBackClick = intents.navigateBack,
            )
        },
        bottomContent = {
            PrimaryButton(
                state = rememberButtonState(loading = saveState.isLoading),
                clickable = Clickable.of(intents.save),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.create))
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

            FormSection(modifier = Modifier.fillMaxWidth()) {
                val bioRequester = remember { FocusRequester() }
                val mainSkillRequester = remember { FocusRequester() }

                ContentLabel(label = resRef(R.string.title)) {
                    SimpleTextField(
                        data = titleField,
                        onValueChange = intents.changeTitle,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        actions = KeyboardActions(onNext = { bioRequester.requestFocus() }),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.about)) {
                    SimpleTextField(
                        data = bioField,
                        onValueChange = intents.changeBio,
                        minLines = 5,
                        maxLines = 5,
                        modifier = Modifier.focusRequester(bioRequester),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.main_skills)) {
                    SkillsField(
                        input = mainSkillInput,
                        skills = mainSkills,
                        onInputChange = intents.changeMainSkillInput,
                        onAdd = intents.addMainSkill,
                        onRemove = intents.removeMainSkill,
                        enabled = true,
                        focusRequester = mainSkillRequester,
                    )
                }

                ContentLabel(label = resRef(R.string.secondary_skills)) {
                    SkillsField(
                        input = secondarySkillInput,
                        skills = secondarySkills,
                        onInputChange = intents.changeSecondarySkillInput,
                        onAdd = intents.addSecondarySkill,
                        onRemove = intents.removeSecondarySkill,
                        enabled = true,
                    )
                }

                ContentLabel(label = resRef(R.string.payment_method)) {
                    SingleSelectChips(
                        options = PaymentType.entries,
                        selected = paymentPreference,
                        label = { it.label.display() },
                        onSelect = intents.changePaymentPreference,
                    )
                }

                ContentLabel(label = resRef(R.string.min_rate)) {
                    SimpleTextField(
                        data = TextFieldData(value = minRate),
                        onValueChange = intents.changeMinRate,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                        ),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.currency)) {
                    SingleSelectChips(
                        options = Currency.entries,
                        selected = currency,
                        label = { it.name },
                        onSelect = intents.changeCurrency,
                    )
                }

                ContentLabel(label = resRef(R.string.availability)) {
                    MultiSelectChips(
                        options = AvailabilityType.entries,
                        selected = availability,
                        label = { it.label.display() },
                        onToggle = intents.toggleAvailability,
                    )
                }

                ContentLabel(label = resRef(R.string.github)) {
                    SimpleTextField(
                        data = TextFieldData(value = githubUrl),
                        onValueChange = intents.changeGithubUrl,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next,
                        ),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.portfolio)) {
                    SimpleTextField(
                        data = TextFieldData(value = portfolioUrl),
                        onValueChange = intents.changePortfolioUrl,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Done,
                        ),
                        borderVisibility = BorderVisibility.Always,
                    )
                }
            }

            VSpacer(padding.bottom)
        }
    }
}
