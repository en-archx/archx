package co.en.archx.sample.app

import android.content.Context
import co.en.archx.sample.app.dimodules.ActivityModule
import co.en.archx.sample.app.dimodules.NetworkModule
import co.en.archx.sample.app.dimodules.RestModule
import co.en.archx.sample.app.dimodules.ViewModelFactoryModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Qualifier
@Named("app-context")
annotation class ArchxAppContext

@AppScope
@Component(modules = [
	ArchxAppModule::class, ViewModelFactoryModule::class,
	NetworkModule::class, RestModule::class, ActivityModule::class])
interface ArchxAppComponent {

    fun inject(app: ArchxSampleApp)

    @ArchxAppContext
    fun context(): Context
}

@Module
class ArchxAppModule(private val app : ArchxSampleApp) {

    @Provides
    @AppScope
    fun app(): ArchxSampleApp = app

    @Provides
    @AppScope
    @ArchxAppContext
    fun context(): Context = app.applicationContext
}