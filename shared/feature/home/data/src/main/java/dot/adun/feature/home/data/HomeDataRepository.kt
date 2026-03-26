package dot.adun.feature.home.data

import dot.adun.feature.home.domain.HomeRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class HomeDataRepository @Inject constructor() : HomeRepository {
    override fun test() {
        println("TEST REPOSITORY DI")
    }
}
