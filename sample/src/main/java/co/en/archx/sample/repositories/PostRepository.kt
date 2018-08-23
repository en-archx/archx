package co.en.archx.sample.repositories

import co.en.archx.sample.app.api.RedditApi
import co.en.archx.sample.app.api.response.RedditResponse
import co.en.archx.sample.ext.PostSource
import io.reactivex.Observable

class PostRepository(private val redditApi: RedditApi) {

    fun getRepo(source: PostSource, cursor: String, limit: Int): Observable<RedditResponse> {

        return when(source) {
            is PostSource.Search ->
                getSearch(source.query, cursor, limit)

            is PostSource.Top ->
                getTopPost(cursor, limit)

            is PostSource.New ->
                getNewPost(cursor, limit)

            is PostSource.Hot ->
                getHotPost(cursor, limit)

            is PostSource.Controversial ->
                getControversialPost(cursor, limit)
        }
    }

    private fun getTopPost(cursor: String = "", limit: Int = 10)
            : Observable<RedditResponse> {
        return redditApi.getTop(cursor, limit)
    }

    private fun getHotPost(cursor: String = "", limit: Int = 10)
            : Observable<RedditResponse> {
        return redditApi.getHot(cursor, limit)
    }

    private fun getNewPost(cursor: String = "", limit: Int = 10)
            : Observable<RedditResponse> {
        return redditApi.getNew(cursor, limit)
    }

    private fun getControversialPost(cursor: String = "", limit: Int = 10)
            : Observable<RedditResponse> {
        return redditApi.getControversial(cursor, limit)
    }

    private fun getSearch(query: String, cursor: String = "", limit: Int = 10)
            : Observable<RedditResponse> {
        return redditApi.getSearch(query, cursor, limit)
    }
}