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
import dot.adun.feature.authorized.ui.screen.create.vacancy.CreateVacancyViewIntents

@Composable
fun VacancyCreateForm(
    titleField: TextFieldData,
    descriptionField: TextFieldData,
    skills: List<String>,
    skillInput: String,
    experienceYears: String,
    paymentMethod: PaymentType,
    budget: String,
    currency: Currency,
    durationType: AvailabilityType,
    saveState: LoadState,
    intents: CreateVacancyViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.vacancy_create_title),
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
                val descriptionRequester = remember { FocusRequester() }
                val skillRequester = remember { FocusRequester() }

                ContentLabel(label = resRef(R.string.title)) {
                    SimpleTextField(
                        data = titleField,
                        onValueChange = intents.changeTitle,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        actions = KeyboardActions(onNext = { descriptionRequester.requestFocus() }),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.vacancy_description)) {
                    SimpleTextField(
                        data = descriptionField,
                        onValueChange = intents.changeDescription,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        minLines = 5,
                        maxLines = 5,
                        actions = KeyboardActions(onNext = { skillRequester.requestFocus() }),
                        borderVisibility = BorderVisibility.Always,
                        modifier = Modifier.focusRequester(descriptionRequester),
                    )
                }

                ContentLabel(label = resRef(R.string.skills)) {
                    SkillsField(
                        input = skillInput,
                        skills = skills,
                        onInputChange = intents.changeSkillInput,
                        onAdd = intents.addSkill,
                        onRemove = intents.removeSkill,
                        enabled = true,
                        focusRequester = skillRequester,
                    )
                }

                ContentLabel(label = resRef(R.string.vacancy_experience)) {
                    SimpleTextField(
                        data = TextFieldData(value = experienceYears),
                        onValueChange = intents.changeExperience,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        borderVisibility = BorderVisibility.Always,
                    )
                }

                ContentLabel(label = resRef(R.string.payment_method)) {
                    SingleSelectChips(
                        options = PaymentType.entries,
                        selected = paymentMethod,
                        label = { it.label.display() },
                        onSelect = intents.changePaymentMethod,
                    )
                }

                ContentLabel(label = resRef(R.string.budget)) {
                    SimpleTextField(
                        data = TextFieldData(value = budget),
                        onValueChange = intents.changeBudget,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done,
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

                ContentLabel(label = resRef(R.string.vacancy_duration)) {
                    SingleSelectChips(
                        options = AvailabilityType.entries,
                        selected = durationType,
                        label = { it.label.display() },
                        onSelect = intents.changeDurationType,
                    )
                }
            }

            VSpacer(padding.bottom)
        }
    }
}
