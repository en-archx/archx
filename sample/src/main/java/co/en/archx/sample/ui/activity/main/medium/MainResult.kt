package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.Result
import co.en.archx.sample.app.domain.RedditChild

sealed class MainResult : Result {

    data class PostLoaded(val posts: List<RedditChild>) : MainResult()

    data class MorePostLoaded(val posts: List<RedditChild>) : MainResult()

    object PostLoading : MainResult()

    data class PostLoadFail(val error: Throwable) : MainResult()

}