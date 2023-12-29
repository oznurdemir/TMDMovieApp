package com.example.tmdmovieapp.network

import com.example.tmdmovieapp.model.MovieDetailResponse
import com.example.tmdmovieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(@Header("Authorization") token : String) : Response<MovieResponse>

    @GET("{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId : String, @Header("Authorization") token : String) : Response<MovieDetailResponse>
}