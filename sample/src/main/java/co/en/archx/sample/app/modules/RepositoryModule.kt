package co.en.archx.sample.app.modules

import co.en.archx.sample.app.AppScope
import co.en.archx.sample.app.api.RedditApi
import co.en.archx.sample.repositories.PostRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @AppScope
    fun postRepository(redditApi: RedditApi): PostRepository {
        return PostRepository(redditApi)
    }

}