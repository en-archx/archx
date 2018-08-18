package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.State
import co.en.archx.sample.app.domain.RedditChildData

class MainState(val post: List<RedditChildData>) : State {

    companion object {

        fun init() = MainState(
                post = emptyList()
        )
    }



}