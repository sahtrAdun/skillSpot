package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyApplicationsParamsDto(
    @SerialName("p_is_active") val isActive: Boolean = true,
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int,
)
