package dot.adun.feature.authorized.ui.screen.create.resume

import dot.adun.core.ui.core.BaseViewIntents
import dot.adun.feature.authorized.domain.entity.AvailabilityType
import dot.adun.feature.authorized.domain.entity.Currency
import dot.adun.feature.authorized.domain.entity.PaymentType

class CreateResumeViewIntents : BaseViewIntents() {
    val navigateBack = intent("navigateBack")
    val changeTitle = typedIntent<String>("changeTitle")
    val changeBio = typedIntent<String>("changeBio")
    val changeMainSkillInput = typedIntent<String>("changeMainSkillInput")
    val addMainSkill = intent("addMainSkill")
    val removeMainSkill = typedIntent<String>("removeMainSkill")
    val changeSecondarySkillInput = typedIntent<String>("changeSecondarySkillInput")
    val addSecondarySkill = intent("addSecondarySkill")
    val removeSecondarySkill = typedIntent<String>("removeSecondarySkill")
    val changePaymentPreference = typedIntent<PaymentType>("changePaymentPreference")
    val changeMinRate = typedIntent<String>("changeMinRate")
    val changeCurrency = typedIntent<Currency>("changeCurrency")
    val toggleAvailability = typedIntent<AvailabilityType>("toggleAvailability")
    val changeGithubUrl = typedIntent<String>("changeGithubUrl")
    val changePortfolioUrl = typedIntent<String>("changePortfolioUrl")
    val save = intent("save")
}
