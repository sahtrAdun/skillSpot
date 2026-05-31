package dot.adun.core.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.core.data.store.PreferencesDataRepository
import dot.adun.core.domain.store.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {
    @Binds
    abstract fun bindPreferencesRepository(
        repository: PreferencesDataRepository
    ): PreferencesRepository
}
