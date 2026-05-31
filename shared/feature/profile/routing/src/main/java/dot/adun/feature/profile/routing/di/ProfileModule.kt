package dot.adun.feature.profile.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.profile.data.ProfileDataRepository
import dot.adun.feature.profile.domain.ProfileRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    abstract fun bindsProfileRepository(
        repository: ProfileDataRepository
    ): ProfileRepository
}
