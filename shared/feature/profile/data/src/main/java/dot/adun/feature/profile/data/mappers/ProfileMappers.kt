package dot.adun.feature.profile.data.mappers

import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.core.domain.mappers.toNetwork
import dot.adun.feature.profile.data.dto.ProfileDto
import dot.adun.feature.profile.data.dto.PublicProfileDto
import dot.adun.feature.profile.data.dto.UserRoleDto
import dot.adun.feature.profile.domain.entity.Profile
import dot.adun.feature.profile.domain.entity.PublicProfile

fun ProfileDto.toDomainModel(): Profile = Profile(
    id = id,
    userId = userId,
    role = role.toUserRole(),
    email = email,
    personalInfo = personalInformation(),
    ratingAvg = ratingAvg,
    tasksCompleted = tasksCompleted,
    avatarUrl = avatarUrl,
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt?.toLocalDateTime()
)

fun Profile.toNetworkModel(): ProfileDto = ProfileDto(
    id = id,
    userId = userId,
    role = role.toNetworkModel(),
    email = email,
    fullName = personalInfo.fullName,
    bio = personalInfo.bio,
    ratingAvg = ratingAvg,
    tasksCompleted = tasksCompleted,
    age = personalInfo.age,
    country = personalInfo.country,
    city = personalInfo.city,
    avatarUrl = avatarUrl,
    createdAt = createdAt.toNetwork(),
    updatedAt = updatedAt?.toNetwork()
)

fun PublicProfileDto.toDomainModel(): PublicProfile = PublicProfile(
    id = id,
    role = role.toUserRole(),
    fullName = fullName,
    bio = bio,
    ratingAvg = ratingAvg,
    tasksCompleted = tasksCompleted,
    avatarUrl = avatarUrl,
    age = age,
    country = country,
    city = city,
    createdAt = createdAt.toLocalDateTime(),
)

fun UserRole.toNetworkModel(): String = when (this) {
    UserRole.Freelancer -> UserRoleDto.FREELANCER.raw
    UserRole.Customer -> UserRoleDto.CUSTOMER.raw
    UserRole.None -> UserRoleDto.NONE.raw
}

fun String.toUserRole(): UserRole = when (this) {
    UserRoleDto.FREELANCER.raw -> UserRole.Freelancer
    UserRoleDto.CUSTOMER.raw -> UserRole.Customer
    else -> UserRole.None
}

private fun ProfileDto.personalInformation(): Profile.PersonalInfo = Profile.PersonalInfo(
    fullName = fullName,
    bio = bio,
    age = age,
    country = country,
    city = city
)
