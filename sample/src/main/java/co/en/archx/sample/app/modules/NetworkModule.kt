package co.en.archx.sample.app.modules

import android.content.Context
import android.util.Log
import co.en.archx.sample.app.AppScope
import co.en.archx.sample.app.ArchxAppContext
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Named

/**
 *
 */
@Module
class NetworkModule {

    @Provides
    @AppScope
    fun picasso(@ArchxAppContext context: Context,
                okHttpClient: OkHttpClient)
            : Picasso {

        return Picasso.Builder(context)
                .downloader(OkHttp3Downloader(okHttpClient)).build()
    }

    @Provides
    @AppScope
    fun okHttpClient(cache: Cache): OkHttpClient {

        val interceptor = HttpLoggingInterceptor {
            message -> Log.i("fm-network", message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache).build()
    }

    @Provides
    @AppScope
    fun cache(@ArchxAppContext context: Context, @Named("cache-directory") cacheDir: String,
              @Named("cache-size") cacheSize: Long)
            : Cache = Cache(File(context.cacheDir, cacheDir), cacheSize)

    @Provides
    @AppScope
    @Named("cache-directory")
    fun cacheDir(): String = "flipmap-cache-dir"

    @Provides
    @AppScope
    @Named("cache-size")
    fun cacheSize(): Long = 128 * 1024 * 1024

    @Provides
    @AppScope
    fun gson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
}