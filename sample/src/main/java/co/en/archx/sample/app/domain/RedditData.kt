package co.en.archx.sample.app.domain

data class RedditData(
        val after: String,
        val children: List<RedditChild>
)