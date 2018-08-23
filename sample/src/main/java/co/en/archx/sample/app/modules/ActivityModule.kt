package co.en.archx.sample.app.modules

import co.en.archx.sample.ui.activity.main.MainActivity
import co.en.archx.sample.ui.activity.main.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module()
abstract class ActivityModule {

    @ContributesAndroidInjector
    @MainScope
    abstract fun contributeMainActivity(): MainActivity

}