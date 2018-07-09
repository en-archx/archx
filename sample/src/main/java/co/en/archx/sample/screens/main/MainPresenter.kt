package co.en.archx.sample.screens.main

import co.en.archx.archx.IPresenter
import co.en.archx.sample.screens.main.transferobjects.MainAction
import co.en.archx.sample.screens.main.transferobjects.MainEvent
import co.en.archx.sample.screens.main.transferobjects.MainResult
import co.en.archx.sample.screens.main.transferobjects.MainState
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class MainPresenter : IPresenter<
        MainEvent,
        MainAction,
        MainResult,
        MainState> (MainState.init()) {

    override fun onInit(data: MutableMap<String, Any>?) {

    }

    override fun reducer(): BiFunction<MainState, MainResult, MainState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun actionToResult(): ObservableTransformer<MainAction, MainResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}