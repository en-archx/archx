package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.Event
import co.en.archx.sample.ext.PostSource

sealed class MainEvent : Event<MainAction> {

    object Init : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.Top)
    }

    data class SearchTextChanged(val query: String) : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.Search(query))
    }

    object TopButtonClicked : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.Top)
    }

    object HotButtonClicked : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.Hot)
    }

    object NewButtonClicked : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.New)
    }

    object ControversialButtonClicked : MainEvent() {
        override fun toAction() = MainAction.LoadPost(PostSource.Controversial)
    }

    object ListPulled : MainEvent() {
        override fun toAction() = MainAction.Refresh
    }
}