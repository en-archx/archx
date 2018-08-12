package co.en.archx.sample.ui.activity.main

import co.en.archx.archx.IPresenter
import co.en.archx.archx.IView
import co.en.archx.sample.ui.activity.main.medium.MainAction
import co.en.archx.sample.ui.activity.main.medium.MainEvent
import co.en.archx.sample.ui.activity.main.medium.MainResult
import co.en.archx.sample.ui.activity.main.medium.MainState

interface MainContract {

    interface View : IView<MainEvent, MainState>

    abstract class Presenter
        : IPresenter<MainEvent, MainAction, MainResult, MainState>(MainState.init())

}