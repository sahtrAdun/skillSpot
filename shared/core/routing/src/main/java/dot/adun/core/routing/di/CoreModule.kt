package dot.adun.core.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.core.data.secret.secret.SecretDataRepository
import dot.adun.core.data.secret.store.PreferencesDataRepository
import dot.adun.core.domain.secret.SecretRepository
import dot.adun.core.domain.store.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {
    @Binds
    abstract fun bindSecretRepository(
        repository: SecretDataRepository
    ): SecretRepository

    @Binds
    abstract fun bindPreferencesRepository(
        repository: PreferencesDataRepository
    ): PreferencesRepository
}
