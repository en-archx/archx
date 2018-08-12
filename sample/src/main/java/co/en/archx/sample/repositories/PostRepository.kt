package co.en.archx.sample.repositories

import co.en.archx.sample.app.api.RedditApi
import co.en.archx.sample.app.api.response.RedditResponse
import io.reactivex.Observable

class PostRepository(val redditApi: RedditApi) {

    fun getTopPost(cursor: String, limit: Int)
            : Observable<RedditResponse> {
        return redditApi.getTop(cursor, limit)
    }

    fun getHotPost(cursor: String, limit: Int)
            : Observable<RedditResponse> {
        return redditApi.getHot(cursor, limit)
    }

    fun getNewPost(cursor: String, limit: Int)
            : Observable<RedditResponse> {
        return redditApi.getNew(cursor, limit)
    }

    fun getControversialPost(cursor: String, limit: Int)
            : Observable<RedditResponse> {
        return redditApi.getControversial(cursor, limit)
    }

    fun getSearch(query: String, cursor: String, limit: Int)
            : Observable<RedditResponse> {
        return redditApi.getSearch(query, cursor, limit)
    }
}