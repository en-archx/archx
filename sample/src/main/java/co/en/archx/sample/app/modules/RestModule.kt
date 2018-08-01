package co.en.archx.sample.app.modules

import co.en.archx.sample.app.api.RedditApi
import co.en.archx.sample.app.AppScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by n-121 on 18/01/2018.
 */
@Module
class RestModule {

    @Provides
    @AppScope
    @Named("reddit-base-url")
    fun redditBaseUrl(): String = "https://www.reddit.com"

    @Provides
    @AppScope
    @Named("reddit-retrofit")
    fun redditRetrofit(@Named("reddit-base-url") url: String, gson: Gson, httpClient: OkHttpClient)
            : Retrofit {

        return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
    }

    @Provides
    @AppScope
    fun redditApi(@Named("reddit-retrofit") retrofit: Retrofit): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

}