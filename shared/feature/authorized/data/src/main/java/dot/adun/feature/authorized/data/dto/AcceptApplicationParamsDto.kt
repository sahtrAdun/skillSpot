package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptApplicationParamsDto(
    @SerialName("p_application_id") val applicationId: String,
)

@Serializable
data class CancelApplicationParamsDto(
    @SerialName("p_application_id") val applicationId: String,
)
