package co.en.archx.sample.app

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class RedditApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerArchxAppComponent.builder()
                .archxAppModule(ArchxAppModule(this))
                .build()
                .inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector

}