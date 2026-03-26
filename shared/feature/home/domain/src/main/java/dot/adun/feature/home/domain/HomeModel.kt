package dot.adun.feature.home.domain

import javax.inject.Inject

class HomeModel @Inject constructor(
    private val repository: HomeRepository
) {
    fun test() = repository.test()
}
