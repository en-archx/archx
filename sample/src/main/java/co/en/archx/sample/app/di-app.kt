package co.en.archx.sample.app

import android.content.Context
import co.en.archx.sample.app.modules.*
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
	NetworkModule::class, RestModule::class, ActivityModule::class,
    RepositoryModule::class])
interface ArchxAppComponent {

    fun inject(app: RedditApp)

    @ArchxAppContext
    fun context(): Context
}

@Module
class ArchxAppModule(private val app : RedditApp) {

    @Provides
    @AppScope
    fun app(): RedditApp = app

    @Provides
    @AppScope
    @ArchxAppContext
    fun context(): Context = app.applicationContext
}