package com.codingwithze.netplix.data.remote

import com.codingwithze.netplix.data.response.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


interface ApiService {

    @GET("movie/now_playing?api_key=5f02b35fae793f6b31e09f00b0834334&language=en-US&page=1")
    fun getNowPlaying(): Call<NowPlayingResponse>

    @GET("movie/{id}?api_key=5f02b35fae793f6b31e09f00b0834334&language=en-US")
    fun getMovieDetail(
        @Path("id") movie_id : Int
    ):Call<MovieDetailResponse>

    @GET("movie/{id}/videos?api_key=5f02b35fae793f6b31e09f00b0834334&language=en-US")
    fun getMovieVideo(
        @Path("id") movie_id : Int
    ):Call<MovieVideoResponse>

    @GET("movie/popular?api_key=5f02b35fae793f6b31e09f00b0834334&language=en-US&page=1")
    fun getPopularNetplix():Call<NowPlayingResponse>

    @GET("trending/movie/day?api_key=5f02b35fae793f6b31e09f00b0834334")
    fun getTrendingMovie():Call<NowPlayingResponse>

    @GET("trending/tv/week?api_key=5f02b35fae793f6b31e09f00b0834334")
    fun getTrendingTv():Call<NowPlayingResponse>

    @GET("search/movie?api_key=5f02b35fae793f6b31e09f00b0834334")
    fun getSearchNetplix(
        @Query("query") title : String
    ):Call<NowPlayingResponse>

}