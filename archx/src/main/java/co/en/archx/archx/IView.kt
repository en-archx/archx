package co.en.archx.archx

import io.reactivex.Observable

interface IView<E: Event<*>, S: State> {

    fun events(): Observable<E>

    fun render(state: S)
}