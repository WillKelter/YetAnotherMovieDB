package com.willk.yetanothermoviedb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willk.yetanothermoviedb.R
import com.willk.yetanothermoviedb.data.Movie
import com.willk.yetanothermoviedb.view.adapters.NowPlayingMoviesAdapter
import com.willk.yetanothermoviedb.view.adapters.PopularMoviesAdapter
import com.willk.yetanothermoviedb.view.viewmodels.MovieViewModel



class ListFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var popularMoviesRecycler: RecyclerView
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    private lateinit var nowPlayingMoviesRecycler: RecyclerView
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMoviesRecycler = view.findViewById(R.id.popularRecyclerView)
        nowPlayingMoviesRecycler = view.findViewById(R.id.nowPlayingRecyclerView)

        getMoviesList(viewModel)
    }

    private fun getMoviesList(viewModel: MovieViewModel){
        popularMoviesRecycler.layoutManager = GridLayoutManager(context,2)
        popularMoviesAdapter = PopularMoviesAdapter(requireContext(), listOf()) { movies -> showMoviesDetails(movies) }

        viewModel.getPopularMoviesObservable()!!.observe(viewLifecycleOwner,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    popularMoviesAdapter.updateMovies(movies)
                    popularMoviesRecycler.adapter = popularMoviesAdapter
                }
            })

       nowPlayingMoviesRecycler.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false )
        nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(requireContext(), listOf()) { movies -> showMoviesDetails(movies) }

        viewModel.getNowPlayingMoviesObservable()!!.observe(viewLifecycleOwner,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    nowPlayingMoviesAdapter.updateNowPlayingMovies(movies)
                    nowPlayingMoviesRecycler.adapter = nowPlayingMoviesAdapter
                }
            })
    }

    private fun showMoviesDetails(movie: Movie) {
        val args: Bundle = Bundle()

        addDetailsToBundle(args,movie)

        val moviesDetailFragment : DetailedFragment =
            DetailedFragment()
        moviesDetailFragment.arguments=args

        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.lo_frame, moviesDetailFragment,"MoviesDetailFragment")
            .addToBackStack("MoviesFragment")
            .commit()
    }

    private fun addDetailsToBundle(args : Bundle,movie : Movie)
    {
        var moviesDetailArrayList : ArrayList<String> = ArrayList<String>()
        moviesDetailArrayList.add(movie.title)
        moviesDetailArrayList.add(movie.backdropPath)
        moviesDetailArrayList.add(movie.posterPath)
        moviesDetailArrayList.add(movie.overview)

        args.putDouble("MoviesRatingScore",movie.rating)
        args.putInt("MoviesDetailVoteCount",movie.vote_count)
        args.putString("MoviesDetailId",movie.id.toString())
        args.putStringArrayList("MoviesDetailArrayList",moviesDetailArrayList)
    }

}