package co.en.archx.sample.ui.activity.main

import android.util.Log
import co.en.archx.sample.ext.PostSource
import co.en.archx.sample.repositories.PostRepository
import co.en.archx.sample.ui.activity.main.medium.MainAction
import co.en.archx.sample.ui.activity.main.medium.MainEvent
import co.en.archx.sample.ui.activity.main.medium.MainResult
import co.en.archx.sample.ui.activity.main.medium.MainState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction

class MainPresenter(private val outputScheduler: Scheduler,
                    private val postRepository: PostRepository) : MainContract.Presenter() {

    private lateinit var source: PostSource

    private var cursor: String = ""

    private var limit: Int = 10

    override fun onInit(vararg data: Any) {
        process(MainEvent.Init)
    }

    override fun reducer(): BiFunction<MainState, MainResult, MainState> {

        return BiFunction { prev, res ->
            when(res) {
                is MainResult.PostLoaded ->
                    prev.postLoaded(res.posts)

                is MainResult.MorePostLoaded ->
                    prev.morePostLoaded(res.posts)

                is MainResult.PostLoading ->
                    prev.postLoading(source)

                is MainResult.PostLoadFail ->
                    prev.postError(res.error)
            }
        }
    }

    override fun actionToResult(): ObservableTransformer<MainAction, MainResult> {

        return ObservableTransformer {
            it.publish {
                Observable.merge(
                        it.ofType(MainAction.LoadPost::class.java)
                                .compose(loadPost()),
                        it.ofType(MainAction.LoadMorePost::class.java)
                                .compose(loadMorePost()),
                        it.ofType(MainAction.Refresh::class.java)
                                .compose(refresh())
                )
            }
        }
    }

    private fun loadPost(): ObservableTransformer<MainAction.LoadPost, MainResult> {

        return ObservableTransformer {
            it.flatMap {
                source = it.source
                cursor = ""
                postRepository.getRepo(it.source, cursor, limit)
                        .map<MainResult> {
                            Log.d("MainResult", it.toString())
                            cursor = it.data.after
                            MainResult.PostLoaded(it.data.children) }
                        .onErrorReturn { MainResult.PostLoadFail(it) }
                        .startWith(MainResult.PostLoading)
                        .observeOn(outputScheduler)
            }
        }
    }

    private fun loadMorePost(): ObservableTransformer<MainAction.LoadMorePost, MainResult> {

        return ObservableTransformer {
            it.filter { cursor.isNotEmpty() }.switchMap {
                postRepository.getRepo(source, cursor, limit)
                        .map<MainResult> {
                            cursor = it.data.after
                            MainResult.MorePostLoaded(it.data.children) }
                        .onErrorReturn { MainResult.PostLoadFail(it) }
                        .startWith(MainResult.PostLoading)
                        .observeOn(outputScheduler)
            }
        }
    }

    private fun refresh(): ObservableTransformer<MainAction.Refresh, MainResult> {

        return ObservableTransformer {
            it.flatMap {
                cursor = ""
                postRepository.getRepo(source, cursor, limit)
                        .map<MainResult> {
                            cursor = it.data.after
                            MainResult.PostLoaded(it.data.children) }
                        .onErrorReturn { MainResult.PostLoadFail(it) }
                        .startWith(MainResult.PostLoading)
                        .observeOn(outputScheduler)
            }
        }
    }
}