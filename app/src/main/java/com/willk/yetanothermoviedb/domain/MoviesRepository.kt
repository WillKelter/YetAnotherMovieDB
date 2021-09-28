package com.willk.yetanothermoviedb.domain

import com.willk.yetanothermoviedb.data.CastAndCrew
import com.willk.yetanothermoviedb.data.Movie

interface MoviesRepository {

    fun getPopularMovies(page: Int = 1, onSuccess: (movies: List<Movie>) -> Unit, onError: () -> Unit)

    fun getNowPlayingMovies(page: Int = 1, onSuccess: (movies: List<Movie>) -> Unit, onError: () -> Unit)

    fun getMovieDetailCredits(movie_id : String, page: Int = 1, onSuccess: (castAndCrew : CastAndCrew) -> Unit, onError: () -> Unit)
}