package co.en.archx.sample.app.modules

import androidx.lifecycle.ViewModelProvider
import co.en.archx.sample.app.AppScope
import co.en.archx.sample.app.RedditPresenterFactory
import co.en.archx.sample.repositories.PostRepository
import dagger.Module
import dagger.Provides

/**
 *
 */
@Module
class ViewModelFactoryModule {

    @Provides
    @AppScope
    fun viewModelFactory(postRepository: PostRepository)
            : ViewModelProvider.Factory {

        return RedditPresenterFactory(postRepository)
    }
}