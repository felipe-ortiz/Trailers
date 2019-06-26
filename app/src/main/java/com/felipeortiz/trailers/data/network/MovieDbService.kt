package com.felipeortiz.trailers.data.network

import android.util.Log
import com.felipeortiz.trailers.data.network.response.TrendingResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "902f0cc9856cfba6f6a98328937c31b7"

interface MovieDbService {
    @GET("trending/{media_type}/{time_window}")
    fun getTrending(@Path("media_type") mediaType: String,
                    @Path("time_window") timeWindow: String
    ) : Deferred<TrendingResponse>

//    @GET("movie/{movie_id}")
//    fun getMovie(@Path("movie_id") movieId: String,
//                 @Query("api_key") apiKey: String,
//                 @Query("append_to_response") appendToResponse: String): Deferred<MovieResponse>


    /**
     * Creates the API service with an interceptor to add the api key to every call.
     *
     * The "operator" keyword allows us to create a MovieDbService by calling it like this "MovieDbService()" instead
     * of explicitly calling invoke()
     */
//    companion object {
//        operator fun invoke(connectivityInterceptor : ConnectivityInterceptor) : MovieDbService {
//            val requestInterceptor = Interceptor { chain ->
//
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("api_key", API_KEY)
//                    .build()
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//                Log.i("MovieDbService", request.toString())
//                return@Interceptor chain.proceed(request)
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .addInterceptor(connectivityInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MovieDbService::class.java)
//        }
//    }
//}
}

val requestInterceptor = Interceptor { chain ->

    val url = chain.request()
        .url()
        .newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .build()
    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()
    Timber.i(request.toString())
    return@Interceptor chain.proceed(request)
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieDbService: MovieDbService = retrofit.create(MovieDbService::class.java)
}