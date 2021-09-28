package com.willk.yetanothermoviedb.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.willk.yetanothermoviedb.R
import com.willk.yetanothermoviedb.data.Movie
import com.willk.yetanothermoviedb.utils.GlideUtils

class PopularMoviesAdapter (context: Context, private var popularMovies: List<Movie>, private val onMovieClick: (movieList: Movie) -> Unit )
    : RecyclerView.Adapter<PopularMoviesAdapter.MoviesViewHolder>() {

    val context : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.popular_movie_item, parent, false)
        return MoviesViewHolder(view)
    }
    override fun getItemCount(): Int = popularMovies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(popularMovies[position])
    }

    fun updateMovies(movies: List<Movie>) {
        this.popularMovies = movies
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.imagePopularMovie)
        private val movieName : TextView = itemView.findViewById(R.id.textPopularMovieName)
        private val movieDate : TextView = itemView.findViewById(R.id.textPopularMovieDate)
        private val voteFirst : TextView = itemView.findViewById(R.id.textPopularMovieVoteFirst)
        private val voteSecond : TextView = itemView.findViewById(R.id.textPopularMovieVoteSecond)

        fun bind(movie: Movie) {
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
            movieName.text=movie.title


            convertToShortDateFormat(movie.releaseDate,movieDate)
            getVoteStrings(movie.rating,voteFirst,voteSecond)

            GlideUtils.urlToImageView("https://image.tmdb.org/t/p/w154${movie.posterPath}",poster,context)
        }
    }

    private fun convertToShortDateFormat(dateString : String,movieDate : TextView)
    {
        val shortDate : String = dateString.substring(0,4)
        movieDate.text=shortDate
    }

    private fun getVoteStrings(rating: Double, voteFirst: TextView, voteSecond: TextView) {
        val voteString : String = rating.toString()
        voteFirst.text=voteString.substring(0,1)
        voteSecond.text=voteString.substring(1,3)
    }
}