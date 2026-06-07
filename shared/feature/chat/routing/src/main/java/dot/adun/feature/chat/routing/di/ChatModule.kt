package dot.adun.feature.chat.routing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dot.adun.feature.chat.data.ChatDataRepository
import dot.adun.feature.chat.domain.ChatRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {
    @Binds
    abstract fun bindsChatRepository(
        repository: ChatDataRepository
    ): ChatRepository
}
