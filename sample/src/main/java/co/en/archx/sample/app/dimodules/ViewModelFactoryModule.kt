package co.en.archx.sample.app.dimodules

import android.arch.lifecycle.ViewModelProvider
import co.en.archx.sample.app.AppScope
import co.en.archx.sample.app.ArchxPresenterFactory
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

        return ArchxPresenterFactory()
    }
}