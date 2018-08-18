package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.Action

sealed class MainAction : Action {

    data class QueryPost(val query: String) : MainAction()

    object ShowTopPost : MainAction()

    object ShowHotPost : MainAction()

    object ShowNewPost : MainAction()

    object ShowControversialPost : MainAction()

    object LoadMorePost : MainAction()

    object Refresh : MainAction()
}