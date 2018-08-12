package co.en.archx.sample.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.en.archx.sample.ui.activity.main.MainPresenter


@Suppress("UNCHECKED_CAST")
/**
 *
 */
class RedditPresenterFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainPresenter::class.java) ->
                    MainPresenter()

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}