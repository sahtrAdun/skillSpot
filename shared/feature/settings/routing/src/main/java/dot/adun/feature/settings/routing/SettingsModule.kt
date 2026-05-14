package dot.adun.feature.settings.routing

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.settings.data.SettingsDataRepository
import dot.adun.feature.settings.domain.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        repository: SettingsDataRepository
    ): SettingsRepository
}
