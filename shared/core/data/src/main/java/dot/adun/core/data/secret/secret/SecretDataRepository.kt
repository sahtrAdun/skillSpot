package dot.adun.core.data.secret.secret

import dot.adun.core.domain.secret.SecretRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class SecretDataRepository @Inject constructor(
) : SecretRepository {
    override fun test() {
        println("test")
    }
}