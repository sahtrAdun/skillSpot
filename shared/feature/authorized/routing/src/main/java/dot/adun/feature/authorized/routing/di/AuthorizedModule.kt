package dot.adun.feature.authorized.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.authorized.data.AuthorizedDataRepository
import dot.adun.feature.authorized.domain.AuthorizedRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorizedModule {
    @Binds
    abstract fun bindsAuthorizedRepository(
        repository: AuthorizedDataRepository
    ): AuthorizedRepository
}
