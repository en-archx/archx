package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.Event

sealed class MainEvent : Event<MainAction> {

    data class SearchTextChanged(val query: String) : MainEvent() {
        override fun toAction() = MainAction.QueryPost(query)
    }

    object TopButtonClicked : MainEvent() {
        override fun toAction() = MainAction.ShowTopPost
    }

    object HotButtonClicked : MainEvent() {
        override fun toAction() = MainAction.ShowHotPost
    }

    object NewButtonClicked : MainEvent() {
        override fun toAction() = MainAction.ShowNewPost
    }

    object ControversialButtonClicked : MainEvent() {
        override fun toAction() = MainAction.ShowControversialPost
    }

    object RefreshPulled : MainEvent() {
        override fun toAction() = MainAction.Refresh
    }
}