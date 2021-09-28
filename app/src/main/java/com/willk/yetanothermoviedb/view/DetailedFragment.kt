package com.willk.yetanothermoviedb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willk.yetanothermoviedb.R
import com.willk.yetanothermoviedb.data.CastAndCrew
import com.willk.yetanothermoviedb.utils.GlideUtils
import com.willk.yetanothermoviedb.view.adapters.CastAndCrewAdapter
import com.willk.yetanothermoviedb.view.viewmodels.CastAndCrewViewModel


class DetailedFragment : Fragment() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var overview: TextView
    private lateinit var info: TextView
    private lateinit var voteFirst: TextView
    private lateinit var voteSecond: TextView

    private lateinit var moviesDetailArrayList: ArrayList<String>
    private var ratingScore: Double = 0.0
    private var id: String = "1"

    private lateinit var castAndCrew: RecyclerView
    private lateinit var castAndCrewAdapter: CastAndCrewAdapter

    val castViewModel : CastAndCrewViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backdrop = view.findViewById(R.id.imageMovieDetailBig)
        poster = view.findViewById(R.id.imageMovieDetail)
        title = view.findViewById(R.id.textMovieDetailName)
        rating = view.findViewById(R.id.movieDetailRatingBar)
        overview = view.findViewById(R.id.textMovieDetailDescription)
        info = view.findViewById(R.id.textMovieDetailInfo)
        voteFirst = view.findViewById(R.id.movieDetailVoteFirst)
        voteSecond = view.findViewById(R.id.movieDetailVoteSecond)

        castAndCrew = view.findViewById(R.id.recyclerViewMovieFullCastAndCrew)

        moviesDetailArrayList = requireArguments().getStringArrayList("MoviesDetailArrayList")!!
        ratingScore = requireArguments().getDouble("MoviesRatingScore",1.0)
        id = requireArguments().getString("MoviesDetailId", "1")

        populateDetails()
    }

    private fun populateDetails() {
        moviesDetailArrayList[1].let { backdropPath ->
            GlideUtils.urlToImageView("https://image.tmdb.org/t/p/w342$backdropPath", backdrop, requireContext())
        }
        moviesDetailArrayList[2].let { posterPath ->
            GlideUtils.urlToImageView("https://image.tmdb.org/t/p/w154$posterPath", poster, requireContext())
        }

        title.text = moviesDetailArrayList[0]
        voteFirst.text = ratingScore.toString().substring(0, 1)
        voteSecond.text = ratingScore.toString().substring(1, 3)
        rating.rating = (ratingScore / 2f).toFloat()
        overview.text = moviesDetailArrayList[3]
        info.text = requireArguments().getInt("MoviesDetailVoteCount", 1).toString() + " People voted"

        getCastAndCrew()
    }

    private fun getCastAndCrew() {
        castAndCrew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        castViewModel.getMoviesCastAndCrewObservable(id)!!.observe(viewLifecycleOwner,
            Observer<CastAndCrew> { castAndCrewData ->
                if (castAndCrewData != null) {
                    castAndCrewAdapter = CastAndCrewAdapter(requireContext(), castAndCrewData.cast, castAndCrewData.crew)
                    castAndCrew.adapter = castAndCrewAdapter
                    castAndCrewAdapter.updateCastAndCrew(castAndCrewData.cast, castAndCrewData.crew)
                }
            })
    }
}