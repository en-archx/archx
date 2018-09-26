package co.en.archx.sample.ui.activity.main

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import co.en.archx.sample.R
import co.en.archx.sample.ext.PostSource
import co.en.archx.sample.ext.ScrollState
import co.en.archx.sample.ext.state
import co.en.archx.sample.ui.activity.main.list.RedditAdapter
import co.en.archx.sample.ui.activity.main.list.RedditListItem
import co.en.archx.sample.ui.activity.main.medium.MainEvent
import co.en.archx.sample.ui.activity.main.medium.MainState
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var adapter: RedditAdapter

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val presenter: MainPresenter by lazy {
        ViewModelProviders.of(this, factory)[MainPresenter::class.java]
    }

    private val disposables = CompositeDisposable()

    private lateinit var state: MainState


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reddit_list.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        reddit_list.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            container.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        presenter.init()
    }

    override fun onResume() {
        super.onResume()

        disposables.add(
                presenter.states().subscribe { render(it) })

        disposables.add(
                events().subscribe { presenter.process(it) })
    }

    override fun onPause() {
        disposables.clear()

        super.onPause()
    }

    override fun events(): Observable<MainEvent> {
        return Observable.mergeArray(
                RxTextView.afterTextChangeEvents(search_edittext)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .filter { textChangeEvent ->
                            (state.source as? PostSource.Search)?.let {
                                // block if state query != to edit text
                                it.query != textChangeEvent.view().text.toString()
                            } ?: textChangeEvent.view().text.toString().isNotEmpty()
                        }
                        .map { MainEvent.SearchTextChanged(it.view().text.toString()) },
                RxView.clicks(top_button)
                        .doOnNext { clearAndUnfocus() }
                        .map { MainEvent.TopButtonClicked },
                RxView.clicks(hot_button)
                        .doOnNext { clearAndUnfocus() }
                        .map { MainEvent.HotButtonClicked },
                RxView.clicks(new_button)
                        .doOnNext { clearAndUnfocus() }
                        .map { MainEvent.NewButtonClicked },
                RxView.clicks(controversial_button)
                        .doOnNext { clearAndUnfocus() }
                        .map { MainEvent.ControversialButtonClicked },
                RxSwipeRefreshLayout.refreshes(refresh_layout)
                        .doOnNext { clearAndUnfocus() }
                        .map { MainEvent.SwipedRefresh },
                RxRecyclerView.scrollEvents(reddit_list)
                        .filter {
                            it.view().state() == ScrollState.HIT_BOTTOM &&
                                    !state.isLoading
                        }
                        .map { MainEvent.ListBottomReached }
        )
    }

    override fun render(state: MainState) {
        this.state = state

        val items = mutableListOf<RedditListItem>()
        items.addAll(
                state.posts
                        .map {
                            val childData = it.data
                            RedditListItem.Reddit(childData.id, childData.author,
                                    childData.subreddit, childData.title, childData.thumbnail,
                                    childData.ups) })

        items.add(RedditListItem.Footer(state.isLoading, state.error))
        adapter.setItems(items)

        top_button.isSelected = state.source === PostSource.Top
        hot_button.isSelected = state.source === PostSource.Hot
        new_button.isSelected = state.source === PostSource.New
        controversial_button.isSelected = state.source === PostSource.Controversial

        if(!state.isLoading)
            refresh_layout.isRefreshing = false
    }

    private fun clearAndUnfocus() {
        search_edittext.text.clear()
        container.requestFocus()
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
    }
}
