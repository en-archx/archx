# archx

`Archx` is an experimental architecture developed to provide consistency in every application component structure, making it predictable or easy to read. 

The architecture is built using `rxjava`. Its reactive nature will serve as an extra layer of abstraction from/to each MVP component. View push events to Presenter using a stream, Presenter will push states to View also using a stream. There is a good reason for doing this, and mainly to manage state. Jake Wharton has a great talk about this, in fact most of the idea here is from that talk, I encourage you to watch it. [Managing states with RxJava by Jake Wharton](https://www.softwaretalks.io/v/1169/managing-state-with-rxjava-by-jake-wharton)

Like any kind of `mvp`, `Archx` have `model-view-presenter`(obviously) but with a buffed presenter.

#### The `Presenter`
`Archx`'s `presenter` extends [Googles AAC](https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample) `ViewModel` for two reasons;

1. It out-lives android-components (`Activity`, `Fragment`) on configuration changes.
2. It can be shared between multiple android-components.

Using `Googles AAC` `ViewModel` will also mean that the consumer of this library will have to implement their own ViewModelFactory.


**The Presenter's anatomy**
```Kotlin
eventRelay
    .map { event.toAction() }
    .compose(actionToResult())
    .scan(initialState, reducer())
    .subscribe { stateRelay.accept(state) }
```
What was shown above is the very core of this architecture, this rest in the `Presenter`. Where going to review each part. 

Notice the two relays `eventRelay` and `stateRelay`, these are the ends of the presenter, it is for input and output respectively.

`eventRelay` is an observable that relays events that is coming for the `View` e.g _mouse-clicks-event_, _list-bottom-reached-event_ or even an _initial-event_.

`stateRelay` is an observable that relays the finish states which were a result from a specific event. The data flowing in this stream is a `state` which holds a specific `Instant` of the ui.

You kinda get here that it has a single direction, events-to-state. A little similar to `Flux` by facebook, if you heard of it.

`actionToResult()` is a method returning an `ObservableTransformmer`, this is the part where the actual logic is executed. Data emitted from `eventRelay` is converted into `action` then this yields `results`. For example, consider a page with a list of dogs, here's the data flow from event to result; _dogs-list-bottom-reached-event_ -> _load-dogs-action_ -> _dogs-loaded-result_.

`initialState` is the first state of the ui

`reducer()` is a `BiFunction` that gets the previous `state` and a `result`, then yields a new `state`. Continuing the example above it will become, _dogs-list-bottom-reached-event_ -> _load-dogs-action_ -> _dogs-loaded-result_ -> _dog-list-state_.

Lastly, the state goes in to the `stateRelay` ready to be emitted to any attached observable.

To understand further, let me give you a concrete version of the example above,

`actionToResult()` often looks like this, its a merge of `action-method`s
```Kotlin
override fun actionToResult(): ObservableTransformer<MainAction, MainResult> {
    return ObservableTransformer { 
        it.publish { 
            Observable.merge(
                    it.ofType(MainAction.DogsListBottomReached::class.java)
                            .compose(loadDogs()),
                    it.ofType(MainAction.AnotherEvent::class.java)
                            .compose(anotherActionMethod())
            )
        }
    }
}
```
This is an example eof an `action-method`. 
```Kotlin
private fun loadDogs(): ObservableTransformer<MainAction.LoadMoreDogs, MainResult> {
    return ObservableTransformer {
        it.flatMap {
            dogsRepository.loadDogs()
                    .map { MainResult.DogsLoaded(it.data) }
                    .onErrorReturn { MainResult.DogsLoadFail(it) }
                    .startWith(MainResult.DogsLoading)
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
```
Theres three parts I want to highlight in this observable transformer. First is the `map { MainResult.DogsLoaded(it.data) }`, the result from different data source, like server for example, is wrap in to a `result`. Now incase error is encoutered, the observable normally breaks and send a terminal data and we dont want that to happen, using `onErrorReturn { MainResult.DogsLoadFail(it) }`, errors will be catched and wrapped as a `result`. Then the `startWith(MainResult.DogsLoading)`, this is called before the `dogsRepository.loadDogs()`.

In the `reducer()` each `result` has its `state` method counterpart.
```Kotlin
override fun reducer(): BiFunction<MainState, MainResult, MainState> {
    return BiFunction { prevState, result ->
        when(result) {
            is MainResult.DogsLoaded ->
                    prevState.dogsLoaded(result.data)

            is MainResult.DogsLoading ->
                    prevState.dogsLoading()

            is MainResult.DogsLoadFail ->
                    prevState.dogsLoadFail(result.error)
        }
    }
}
```

Now lets look at the `state`

```Kotlin
data class MainState(val dogs: List<Dogs>,
                     val isLoading: Boolean,
                     val error: Throwable?) : State {

    companion object {

        fun initial() = MainState(emptyList(), false, null)
    }

    fun dogsLoaded(dogs: List<Dog>): MainState {
        return copy(
                dogs = dogs,
                isLoading = false,
                error = null
        )
    }
    
    fun dogsLoading(): MainState {
        return copy(
                isLoading = true,
                error = null
        )
    }
    
    fun dogsLoadFail(error: Throwable): MainState {
        return copy(
                isLoading = false,
                error = error
        )
    }
}
```

This state will be rendered by the view, the `dogs` will populate a `RecyclerView` for example.
`isLoading` if true, will show a `ProgressBar`. 
Then `error` will show an error toast.

#### The `View`
The view looks like this
```Kotlin
interface MainView {

    fun event(): Observable<MainEvent>
    
    fun render(state: MainState)
}
```



If you notice something wrong, or you foresee a problem that this architecture will encounter, feel free to write an issue. 
**And PR's are very welcome.** I will be uploading a sample application soon that uses this architecture.

### To install the library

#### Gradle
Put this on your root-level `build.gradle`
```gradle
maven {
    url "https://dl.bintray.com/novo-dimaporo/archx"
}
```
Then put this in your module-level `build.gradle`
```gradle
implementation 'co.en.archx:archx:0.0.4'
```
You might also need to import rxjava library that `archx` depends on
```gradle
implementation 'io.reactivex.rxjava2:rxjava:2.1.4'
implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
implementation 'com.jakewharton.rxrelay2:rxrelay:2.0.0'
```
