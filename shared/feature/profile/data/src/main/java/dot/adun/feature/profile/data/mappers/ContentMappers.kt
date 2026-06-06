package dot.adun.feature.profile.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.profile.data.dto.ContentResumeDto
import dot.adun.feature.profile.data.dto.ContentVacancyDto
import dot.adun.feature.profile.data.dto.ProfileContentWrapperDto
import dot.adun.feature.profile.data.dto.ReviewWithSenderDto
import dot.adun.feature.profile.data.dto.UserRoleDto
import dot.adun.feature.profile.domain.entity.ProfileContent
import dot.adun.feature.profile.domain.entity.ProfileResume
import dot.adun.feature.profile.domain.entity.ProfileVacancy
import dot.adun.feature.profile.domain.entity.Review
import kotlinx.serialization.json.Json

private val contentJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun ReviewWithSenderDto.toDomainModel(): Review = Review(
    id = reviewId,
    projectId = projectId,
    rating = rating.toInt(),
    comment = comment,
    createdAt = createdAt.toLocalDateTime(),
    senderId = senderId,
    senderName = senderName,
    senderAvatar = senderAvatar,
)

fun List<ProfileContentWrapperDto>.toProfileContent(): ProfileContent {
    if (isEmpty()) return ProfileContent.Empty

    return when (first().userRole) {
        UserRoleDto.FREELANCER.raw -> ProfileContent.Resumes(
            items = map { wrapper ->
                contentJson
                    .decodeFromJsonElement(ContentResumeDto.serializer(), wrapper.contentItem)
                    .toDomainModel()
            }
        )

        UserRoleDto.CUSTOMER.raw -> ProfileContent.Vacancies(
            items = map { wrapper ->
                contentJson
                    .decodeFromJsonElement(ContentVacancyDto.serializer(), wrapper.contentItem)
                    .toDomainModel()
            }
        )

        else -> ProfileContent.Empty
    }
}

fun ContentVacancyDto.toDomainModel(): ProfileVacancy = ProfileVacancy(
    id = id,
    title = title,
    skills = requiredSkills,
    budget = budget,
    currency = currency,
    paymentMethod = paymentMethod,
    status = status,
)

fun ContentResumeDto.toDomainModel(): ProfileResume = ProfileResume(
    id = id,
    title = title,
    skills = mainSkills,
    minRate = minRate,
    currency = currency,
    paymentPreference = paymentPreference,
    isActive = isActive,
)
