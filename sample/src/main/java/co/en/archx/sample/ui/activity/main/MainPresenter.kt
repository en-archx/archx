package co.en.archx.sample.ui.activity.main

import co.en.archx.archx.IPresenter
import co.en.archx.sample.ui.activity.main.medium.MainAction
import co.en.archx.sample.ui.activity.main.medium.MainEvent
import co.en.archx.sample.ui.activity.main.medium.MainResult
import co.en.archx.sample.ui.activity.main.medium.MainState
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class MainPresenter : MainContract.Presenter() {

    override fun onInit(vararg data: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reducer(): BiFunction<MainState, MainResult, MainState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun actionToResult(): ObservableTransformer<MainAction, MainResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}