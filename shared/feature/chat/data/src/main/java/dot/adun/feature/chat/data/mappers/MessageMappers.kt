package dot.adun.feature.chat.data.mappers

import dot.adun.core.domain.mappers.toLocalDateTime
import dot.adun.feature.chat.data.dto.MessageDto
import dot.adun.feature.chat.domain.entity.Message

fun MessageDto.toDomainModel(): Message = Message(
    id = id,
    projectId = projectId,
    senderId = senderId,
    senderName = senderName,
    senderAvatarUrl = senderAvatarUrl,
    content = content,
    createdAt = createdAt.toLocalDateTime(),
)
