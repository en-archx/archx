package co.en.archx.archx;

import android.arch.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.Map;

import co.en.archx.archx.transferobjects.Action;
import co.en.archx.archx.transferobjects.Event;
import co.en.archx.archx.transferobjects.Result;
import co.en.archx.archx.transferobjects.State;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public abstract class IPresenter<
        E extends Event<A>,
        A extends Action,
        R extends Result,
        S extends State> extends ViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();

    private boolean isInitialized = false;

    private PublishRelay<E> eventRelay = PublishRelay.create();

    private BehaviorRelay<S> stateRelay = BehaviorRelay.create();

    public IPresenter(S initialState) {
        disposables.add(
                eventRelay.map(
                        new Function<E, A>() {
                            @Override
                            public A apply(E event) {
                                return event.toAction();
                            }
                        }
                )
                .compose(actionToResult())
                .scan(initialState, reducer())
                .subscribe(
                        new Consumer<S>() {
                            @Override
                            public void accept(S state) {
                                stateRelay.accept(state);
                            }
                        }
                )
        );
    }

    public Observable<E> events() {
        return eventRelay.hide();
    }

    public Observable<S> states() {
        return stateRelay.hide();
    }

    public void process(E event) {
        eventRelay.accept(event);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public void init(Map<String, Object> data) {
        if(isInitialized) return;

        isInitialized = true;
        onInit(data);
    }

    protected abstract void onInit(Map<String, Object> data);

    protected abstract BiFunction<S, R, S> reducer();

    protected abstract ObservableTransformer<A, R> actionToResult();
}
