package com.example.movieapp.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.FavoriteMovie

class FavoriteMovieAdapter(
    private val context: Context,
    private var favoriteMovies: List<FavoriteMovie> = emptyList(),
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }

    inner class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(favoriteMovie: FavoriteMovie) {
            with(binding) {
                tvJudulFilm.text = favoriteMovie.judul
                tvGenreFilm.text = favoriteMovie.genre
                tvRatingFilm.text = favoriteMovie.rating.toString()
                tvTahunTerbit.text = favoriteMovie.tahun.toString()

                Glide.with(context)
                    .load(favoriteMovie.poster)
                    .placeholder(R.drawable.poster_film)
                    .error(R.drawable.poster_film)
                    .into(imageView)

                itemView.setOnClickListener {
                    listener.onItemClick(favoriteMovie)
                }
            }
        }
    }

    fun updateFavoriteMovies(newFavoriteMovies: List<FavoriteMovie>) {
        favoriteMovies = newFavoriteMovies
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return FavoriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val favoriteMovie = favoriteMovies[position]
        holder.bind(favoriteMovie)
    }

    override fun getItemCount(): Int = favoriteMovies.size
}
