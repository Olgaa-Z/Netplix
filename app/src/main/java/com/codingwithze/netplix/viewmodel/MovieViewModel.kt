package com.codingwithze.netplix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithze.netplix.data.remote.ApiService
import com.codingwithze.netplix.data.response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val api : ApiService) : ViewModel(){

    private val _nowPlayingMovie = MutableLiveData<List<NowPlayingItem>>()
    val nowPlayingMovie : LiveData<List<NowPlayingItem>> = _nowPlayingMovie

    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetail : LiveData<MovieDetailResponse> = _movieDetail

    private val _movieVideo = MutableLiveData<MovieVideoResponse>()
    val movieVideo : LiveData<MovieVideoResponse> = _movieVideo

    private val _popularNetplix = MutableLiveData<List<NowPlayingItem>>()
    val popularNetplix : LiveData<List<NowPlayingItem>> = _popularNetplix

    private val _trendingMovie = MutableLiveData<List<NowPlayingItem>>()
    val trendingMovie : LiveData<List<NowPlayingItem>> = _trendingMovie

    private val _trendingTv = MutableLiveData<List<NowPlayingItem>>()
    val trendingTv : LiveData<List<NowPlayingItem>> = _trendingTv

    private val _searchNetplix = MutableLiveData<List<NowPlayingItem>>()
    val searchNetplix : LiveData<List<NowPlayingItem>> = _searchNetplix



    fun callGetNowPlayingMovie(){
        api.getNowPlaying().enqueue(object  : Callback<NowPlayingResponse>{
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _nowPlayingMovie.postValue(data.results as List<NowPlayingItem>)
                    }else{
                        Log.e("Error : ", "onFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.d("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun callGetPopularNetplix(){
        api.getPopularNetplix().enqueue(object : Callback<NowPlayingResponse>{
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
               if (response.isSuccessful){
                   val data = response.body()
                   if (data != null){
                       _popularNetplix.postValue(data.results as List<NowPlayingItem>?)
                   }
               }else{
                   Log.e("Error : ", response.message())
               }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

//    DETAIL MOVIE
    fun callGetMovieDetail(movie_id : Int){
        api.getMovieDetail(movie_id).enqueue(object  : Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _movieDetail.postValue(data)
                    }
                }else{
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun callGetMovieVideo(movie_id : Int){
        api.getMovieVideo(movie_id).enqueue(object : Callback<MovieVideoResponse>{
            override fun onResponse(
                call: Call<MovieVideoResponse>,
                response: Response<MovieVideoResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _movieVideo.postValue(data)
                    }
                }else{
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<MovieVideoResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }
        })
    }

    fun callGetTrendingMovie(){
        api.getTrendingMovie().enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _trendingMovie.postValue(data.results as List<NowPlayingItem>?)
                    }
                }else{
                    Log.e("Error : ","onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun callGetTrendingTv(){
        api.getTrendingTv().enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _trendingMovie.postValue(data.results as List<NowPlayingItem>?)
                    }
                }else{
                    Log.e("Error : ","onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun callGetSearchNetplix(title : String){
        api.getSearchNetplix(title).enqueue(object  : Callback<NowPlayingResponse>{
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _searchNetplix.postValue(data.results as List<NowPlayingItem>?)
                    }
                }else{
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }
}