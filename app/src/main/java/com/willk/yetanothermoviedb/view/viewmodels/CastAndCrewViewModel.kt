package com.willk.yetanothermoviedb.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willk.yetanothermoviedb.data.CastAndCrew
import com.willk.yetanothermoviedb.domain.MoviesRepository
import com.willk.yetanothermoviedb.domain.MoviesRepositoryImpl

class CastAndCrewViewModel() : ViewModel() {

    private var moviesRepository : MoviesRepository = MoviesRepositoryImpl()

        private var moviesCastAndCrew : MutableLiveData<CastAndCrew>?=null



    init {
        moviesCastAndCrew= MutableLiveData()


    }

    fun getMoviesCastAndCrewObservable(movie_id : String): LiveData<CastAndCrew>? {
        moviesRepository.getMovieDetailCredits(movie_id,
            onSuccess = { it -> moviesCastAndCrew?.setValue(it)}
            ,onError = { Log.d("error","onError")})
        return moviesCastAndCrew
    }



}