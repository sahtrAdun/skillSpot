package dot.adun.feature.login.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.login.data.LoginDataRepository
import dot.adun.feature.login.domain.LoginRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(
        repository: LoginDataRepository
    ): LoginRepository
}
