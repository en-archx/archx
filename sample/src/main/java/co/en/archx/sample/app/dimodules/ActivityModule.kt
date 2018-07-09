package co.en.archx.sample.app.dimodules

import android.app.Activity
import co.en.archx.sample.screens.main.MainActivity
import co.en.archx.sample.screens.main.MainActivitySubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindChatActivityInjectorFactory(builder: MainActivitySubcomponent.Builder)
            : AndroidInjector.Factory<out Activity>

}