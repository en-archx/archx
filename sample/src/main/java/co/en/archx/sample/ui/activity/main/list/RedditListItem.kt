package co.en.archx.sample.ui.activity.main.list

sealed class RedditListItem {

    data class Reddit(
            val id: String,
            val author: String,
            val subreddit: String,
            val title: String,
            val thumbnail: String,
            val ups: Int
    ) : RedditListItem()

    data class Footer(
            val isLoading: Boolean,
            val error: Throwable?
    ) : RedditListItem()

}