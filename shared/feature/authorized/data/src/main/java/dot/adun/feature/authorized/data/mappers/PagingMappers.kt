package dot.adun.feature.authorized.data.mappers

import dot.adun.feature.authorized.data.dto.PagingParamsDto
import dot.adun.feature.authorized.domain.entity.PagingParams

fun PagingParams.toNetworkModel(): PagingParamsDto = PagingParamsDto(
    limit = limit,
    offset = offset
)
