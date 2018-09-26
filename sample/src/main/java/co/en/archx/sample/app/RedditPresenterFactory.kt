package co.en.archx.sample.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.en.archx.sample.repositories.PostRepository
import co.en.archx.sample.ui.activity.main.MainPresenter
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers


@Suppress("UNCHECKED_CAST")
/**
 *
 */
class RedditPresenterFactory(private val postRepository: PostRepository)
    : ViewModelProvider.NewInstanceFactory() {

    private val outputScheduler: Scheduler = AndroidSchedulers.mainThread()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainPresenter::class.java) ->
                    MainPresenter(outputScheduler, postRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}