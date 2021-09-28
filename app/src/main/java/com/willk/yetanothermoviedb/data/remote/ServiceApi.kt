package com.willk.yetanothermoviedb.data.remote


import com.willk.yetanothermoviedb.data.CastAndCrew
import com.willk.yetanothermoviedb.data.GetMoviesResponse
import com.willk.yetanothermoviedb.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int ): Call<GetMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieDetailCredits(
        @Path("movie_id") movie_id : String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int ): Call<CastAndCrew>
}