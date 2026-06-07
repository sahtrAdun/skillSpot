package dot.adun.feature.authorized.ui.screen.create.vacancy

import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType

class CreateVacancyViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val changeTitle = typedIntent<String>("changeTitle")
    val changeDescription = typedIntent<String>("changeDescription")
    val changeSkillInput = typedIntent<String>("changeSkillInput")
    val addSkill = intent("addSkill")
    val removeSkill = typedIntent<String>("removeSkill")
    val changeExperience = typedIntent<String>("changeExperience")
    val changePaymentMethod = typedIntent<PaymentType>("changePaymentMethod")
    val changeBudget = typedIntent<String>("changeBudget")
    val changeCurrency = typedIntent<Currency>("changeCurrency")
    val changeDurationType = typedIntent<AvailabilityType>("changeDurationType")
    val save = intent("save")
}
