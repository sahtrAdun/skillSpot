package dot.adun.feature.authorized.domain.entity

import dot.adun.common.resources.Res
import dot.adun.core.domain.entity.TextRef
import dot.adun.core.domain.entity.resRef
import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Resume(
    val id: String,
    val freelancerId: String,
    val title: String,
    val bio: String?,
    val links: Links,
    val skills: Skills,
    val paymentInfo: PaymentInfo,
    val availability: List<AvailabilityType>,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    @Immutable
    data class Links(
        val githubUrl: String?,
        val portfolioUrl: String?
    )

    @Immutable
    data class Skills(
        val main: List<String>,
        val secondary: List<String>?
    )

    @Immutable
    data class PaymentInfo(
        val preference: PaymentType,
        val minRate: Double,
        val currency: Currency
    )

    val activeLabel: TextRef
        get() = if (isActive) resRef(Res.strings.resume_active)
        else resRef(Res.strings.resume_inactive)
}
