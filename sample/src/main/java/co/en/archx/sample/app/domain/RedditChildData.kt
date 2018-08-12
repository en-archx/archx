package co.en.archx.sample.app.domain

data class RedditChildData(
        val author: String,
        val subreddit: String,
        val title: String,
        val thumbnail: String,
        val ups: Int
)