package co.en.archx.sample.app.domain

data class RedditChildData(
        val id: String,
        val author: String,
        val subreddit: String,
        val title: String,
        val thumbnail: String,
        val ups: Int
)