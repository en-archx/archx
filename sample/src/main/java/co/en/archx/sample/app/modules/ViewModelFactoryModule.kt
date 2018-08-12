package co.en.archx.sample.app.modules

import android.arch.lifecycle.ViewModelProvider
import co.en.archx.sample.app.AppScope
import co.en.archx.sample.app.RedditPresenterFactory
import dagger.Module
import dagger.Provides

/**
 *
 */
@Module
class ViewModelFactoryModule {

    @Provides
    @AppScope
    fun viewModelFactory(): ViewModelProvider.Factory {

        return RedditPresenterFactory()
    }
}