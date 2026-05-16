package dot.adun.feature.register.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.register.data.RegistrationDataRepository
import dot.adun.feature.register.domain.RegistrationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RegistrationModule {
    @Binds
    abstract fun bindRegistrationRepository(
        repository: RegistrationDataRepository
    ): RegistrationRepository
}
