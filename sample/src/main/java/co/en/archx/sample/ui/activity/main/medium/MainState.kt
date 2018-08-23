package co.en.archx.sample.ui.activity.main.medium

import co.en.archx.archx.medium.State
import co.en.archx.sample.app.domain.RedditChild
import co.en.archx.sample.ext.PostSource

data class MainState(val source: PostSource,
                     val posts: List<RedditChild>,
                     val isLoading: Boolean,
                     val error: Throwable?) : State {

    companion object {

        fun init() = MainState(
                source = PostSource.Top,
                posts = emptyList(),
                isLoading = false,
                error = null
        )
    }

    fun postLoaded(posts: List<RedditChild>): MainState {
        return postLoaded(posts, false)
    }

    fun morePostLoaded(posts: List<RedditChild>): MainState {
        return postLoaded(posts, true)
    }

    private fun postLoaded(posts: List<RedditChild>, append: Boolean): MainState {
        val newPosts = mutableListOf<RedditChild>()

        if(append)
            newPosts.addAll(this.posts)

        newPosts.addAll(posts)

        return copy(
                posts = newPosts,
                isLoading = false,
                error = null
        )
    }

    fun postLoading(source: PostSource): MainState {

        return copy(
                source = source,
                isLoading = true,
                error = null
        )
    }

    fun postError(throwable: Throwable): MainState {

        return copy(
                isLoading = false,
                error = throwable
        )
    }
}