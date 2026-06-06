package dot.adun.feature.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ProfileContentWrapperDto(
    @SerialName("user_role") val userRole: String,
    @SerialName("content_item") val contentItem: JsonObject,
)
