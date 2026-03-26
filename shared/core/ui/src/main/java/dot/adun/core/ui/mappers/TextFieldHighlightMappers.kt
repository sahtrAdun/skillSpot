package dot.adun.core.ui.mappers

import dot.adun.core.domain.validation.Explanation
import dot.adun.core.ui.entity.TextFieldData

fun Explanation.helper(): TextFieldData.Helper = TextFieldData.Helper(
    explanation = this
)
