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

class NowPlayingMoviesAdapter(context: Context, private var movies: List<Movie>, private val onMovieClick: (movieList: Movie) -> Unit )
    : RecyclerView.Adapter<NowPlayingMoviesAdapter.MovieViewHolder>() {

    val context : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.now_playing_item, parent, false)
        return MovieViewHolder(view)
    }
    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateNowPlayingMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.imageMovieTV)
        private val movieName : TextView = itemView.findViewById(R.id.titleMovieTV)
        fun bind(movie: Movie) {
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
            movieName.text=movie.title
            GlideUtils.urlToImageView("https://image.tmdb.org/t/p/w154${movie.posterPath}",poster,context)
        }
    }
}