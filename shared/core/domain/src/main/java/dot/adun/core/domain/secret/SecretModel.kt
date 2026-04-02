package dot.adun.core.domain.secret

import javax.inject.Inject

class SecretModel @Inject constructor(
    private val secretRepository: SecretRepository
) {
    fun test() = secretRepository.test()
}
