package co.en.archx.sample.screens.main

import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope

@MainActivityScope
@Subcomponent(modules = [MainMvvmModule::class])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {

        abstract fun mainMvpModule(mainMvvmModule: MainMvvmModule): Builder

        override fun seedInstance(instance: MainActivity) {
            mainMvpModule(MainMvvmModule(instance))
        }
    }
}

@Module
class MainMvvmModule(private val activity: MainActivity) {

}