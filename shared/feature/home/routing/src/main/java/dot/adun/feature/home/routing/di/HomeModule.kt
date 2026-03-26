package dot.adun.feature.home.routing.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.home.data.HomeDataRepository
import dot.adun.feature.home.domain.HomeModel
import dot.adun.feature.home.domain.HomeRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindHomeRepository(
        repository: HomeDataRepository
    ): HomeRepository
}
