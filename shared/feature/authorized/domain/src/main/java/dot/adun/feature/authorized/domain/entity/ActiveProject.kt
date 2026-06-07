package dot.adun.feature.authorized.domain.entity

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class ActiveProject(
    val projectId: String,
    val status: String,
    val createdAt: LocalDateTime,
    val vacancyId: String,
    val vacancyTitle: String,
    val vacancyBudget: Double,
    val vacancyCurrency: Currency,
    val freelancerId: String,
    val freelancerName: String?,
    val freelancerAvatar: String?,
    val resumeId: String,
    val resumeTitle: String,
)
