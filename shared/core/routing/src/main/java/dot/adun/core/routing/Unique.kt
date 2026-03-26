package dot.adun.core.routing

import dot.adun.core.domain.util.randomUuid
import kotlinx.serialization.Serializable

@Serializable
abstract class Unique(
    open val customId: String? = null
) : Navigation  {
    override val id: String = customId ?: randomUuid()
}
