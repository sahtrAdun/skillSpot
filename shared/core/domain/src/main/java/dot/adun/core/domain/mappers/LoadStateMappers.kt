package dot.adun.core.domain.mappers

import dot.adun.core.domain.entity.LoadState

val LoadState.isLoading: Boolean
    get() = this is LoadState.Loading
