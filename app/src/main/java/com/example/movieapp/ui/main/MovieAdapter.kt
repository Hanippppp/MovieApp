package com.example.movieapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.Movie
import com.bumptech.glide.Glide


class MovieAdapter(context: Context, private var listMovie: ArrayList<Movie>?) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listener : FireBaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie?.get(position)
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            listener.onDataClick(movie)
        }
    }

    override fun getItemCount(): Int = listMovie?.size!!

    inner class MovieViewHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(movie: Movie?){
            binding.tvJudulFilm.text = movie?.judul
            binding.tvGenreFilm.text = movie?.genre
            binding.tvRatingFilm.text = movie?.rating.toString()
            binding.tvTahunTerbit.text = movie?.tahun.toString()

            Glide.with(itemView)
                .load(movie?.poster)
                .placeholder(R.drawable.poster_film)
                .error(R.drawable.poster_film)
                .into(binding.imageView)
        }
    }
    fun setSearchedList(listMovie: ArrayList<Movie>) {
        this.listMovie = listMovie
        notifyDataSetChanged()
    }


    interface FireBaseDataListener {
        fun onDataClick(movie: Movie?)
    }

    init {
        listener = context as FireBaseDataListener
    }
}