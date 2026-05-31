package dot.adun.feature.authorized.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingParamsDto(
    @SerialName("p_limit") val limit: Int,
    @SerialName("p_offset") val offset: Int
)