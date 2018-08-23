package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.Action
import co.en.archx.sample.ext.PostSource

sealed class MainAction : Action {

    data class LoadPost(val source: PostSource) : MainAction()

    object LoadMorePost : MainAction()

    object Refresh : MainAction()
}