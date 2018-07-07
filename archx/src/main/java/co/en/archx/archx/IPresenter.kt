package co.en.archx.archx

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

@Suppress("LeakingThis")
abstract class IPresenter<
        E: Event<A>,
        A: Action,
        R: Result,
        S: State>(initState: S) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val eventRelay: PublishRelay<E> = PublishRelay.create()

    private val stateRelay: BehaviorRelay<S> = BehaviorRelay.create()

    val events: Observable<E>
        get() = events.hide()

    val states: Observable<S>
        get() = states.hide()

    init {
        disposables.add(eventRelay
                .map { it.toAction() }
                .compose(actionToResult())
                .scan(initState, reducer())
                .subscribe { stateRelay.accept(it) })
    }

    abstract fun reducer(): BiFunction<S, R, S>

    abstract fun actionToResult(): ObservableTransformer<A, R>

    fun process(event: E) {
        eventRelay.accept(event)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}