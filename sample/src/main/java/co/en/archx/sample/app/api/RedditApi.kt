package co.en.archx.sample.app.api

import co.en.archx.sample.app.api.response.RedditResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    fun getTop(
            @Query("after") cursor: String,
            @Query("limit") limit: Int
    ) : Observable<RedditResponse>

    @GET("/hot.json")
    fun getHot(
            @Query("after") cursor: String,
            @Query("limit") limit: Int
    ) : Observable<RedditResponse>

    @GET("/new.json")
    fun getNew(
            @Query("after") cursor: String,
            @Query("limit") limit: Int
    ) : Observable<RedditResponse>

    @GET("/controversial.json")
    fun getControversial(
            @Query("after") cursor: String,
            @Query("limit") limit: Int
    ) : Observable<RedditResponse>

    @GET("/search.json")
    fun getSearch(
            @Query("q") query: String,
            @Query("after") cursor: String,
            @Query("limit") limit: Int
    ) : Observable<RedditResponse>
}