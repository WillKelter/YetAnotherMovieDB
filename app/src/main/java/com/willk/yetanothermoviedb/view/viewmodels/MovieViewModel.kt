package com.willk.yetanothermoviedb.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willk.yetanothermoviedb.data.Movie
import com.willk.yetanothermoviedb.domain.MoviesRepository
import com.willk.yetanothermoviedb.domain.MoviesRepositoryImpl

class MovieViewModel() : ViewModel() {

    private var moviesRepository : MoviesRepository = MoviesRepositoryImpl()
    private var popularMoviesList: MutableLiveData<List<Movie>>? = null
    private var nowPlayingMoviesList: MutableLiveData<List<Movie>>? = null

    init {
        popularMoviesList = MutableLiveData()
        moviesRepository.getPopularMovies(onSuccess = { it -> popularMoviesList?.setValue(it)},onError = { Log.d("error","onError")})

        nowPlayingMoviesList = MutableLiveData()
        moviesRepository.getNowPlayingMovies(onSuccess = {it -> nowPlayingMoviesList?.setValue(it)},onError = {Log.d("error","onError")})
    }

    fun getPopularMoviesObservable() : LiveData<List<Movie>>? {
        return popularMoviesList
    }

    fun getNowPlayingMoviesObservable(): LiveData<List<Movie>>? {
        return nowPlayingMoviesList
    }
}