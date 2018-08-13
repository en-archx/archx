/*
  Copyright (c) 2018-present, Archx Contributors.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package co.en.archx.archx;

import android.arch.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import co.en.archx.archx.medium.Action;
import co.en.archx.archx.medium.Event;
import co.en.archx.archx.medium.Result;
import co.en.archx.archx.medium.State;
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

    public Observable<S> states() {
        return stateRelay.hide();
    }

    public void process(E event) {
        eventRelay.accept(event);
    }

    public void init(Object ... data) {
        if(isInitialized) return;

        isInitialized = true;
        onInit(data);
    }

    protected abstract void onInit(Object ... data);

    protected abstract BiFunction<S, R, S> reducer();

    protected abstract ObservableTransformer<A, R> actionToResult();

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
