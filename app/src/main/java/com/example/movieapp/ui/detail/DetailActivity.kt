package com.example.movieapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.R.id.toolbar2
import com.example.movieapp.databinding.DetailActivityBinding
import com.example.movieapp.model.FavoriteMovie
import com.example.movieapp.model.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")

    private lateinit var binding: DetailActivityBinding
    private lateinit var movie: Movie
    private lateinit var favViewModel: FavViewModel
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.detail_activity)

        favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        val judul = intent.getStringExtra(EXTRA_JUDUL) ?: ""
        Log.d("DetailActivity", "Received judul: $judul")

        Log.d("DetailActivity", "Judul to search: $judul")
        favViewModel.getFavoriteMovieByJudul(judul).observe(this) { favoriteMovie ->
            Log.d("DetailActivity", "Favorite movie found: $favoriteMovie")
            if (favoriteMovie != null) {
                Log.d("DetailActivity", "Found favorite movie: $favoriteMovie")
                isFavorite = true
                updateUI(favoriteMovie)
                updateFabIcon() // Update FAB icon
            } else {
                Log.e("DetailActivity", "Favorite movie not found")
            }
        }

        val toolbar: Toolbar = findViewById(toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            toggleFavorite()
        }

        movie = intent.getParcelableExtra(EXTRA_MOVIE) ?: throw IllegalArgumentException("Movie not found")
        Log.d("DetailActivity", "Received movie: $movie")
        val imageView = findViewById<ImageView>(R.id.imageView2)

        isMovieFavorited(movie) { isFavorited ->

            if (isFavorited) {
                isFavorite = true
                updateFabIcon()
            } else {

            }
        }

        if (movie != null) {
            val movieJudul = movie.judul
            val movieDesc = movie.desc
            val movieGenre = movie.genre
            val moviePoster = movie.poster
            val movieRating = movie.rating
            val movieRelease = movie.tahun
            val movieDirector = movie.director

            findViewById<TextView>(R.id.textViewJudul).text = movieJudul
            findViewById<TextView>(R.id.textViewDesc).text = movieDesc
            findViewById<TextView>(R.id.textViewGenre).text = movieGenre
            findViewById<TextView>(R.id.textViewDirector).text = movieDirector
            findViewById<TextView>(R.id.textViewRating).text = movieRating.toString()
            findViewById<TextView>(R.id.textViewTahun).text = movieRelease.toString()

            Glide.with(this)
                .load(moviePoster)
                .into(imageView)
        } else {

            Toast.makeText(this, "Movie object is null", Toast.LENGTH_SHORT).show()
            finish()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
    private fun updateUI(favoriteMovie: FavoriteMovie) {
        binding.textViewJudul.text = favoriteMovie.judul
        binding.textViewDesc.text = favoriteMovie.desc
        binding.textViewDirector.text = favoriteMovie.director
        binding.textViewGenre.text = favoriteMovie.genre
        binding.textViewRating.text = favoriteMovie.rating.toString()
        binding.textViewTahun.text = favoriteMovie.tahun.toString()

        Glide.with(this)
            .load(favoriteMovie.poster)
            .placeholder(R.drawable.poster_film)
            .error(R.drawable.poster_film)
            .into(binding.imageView2)

        isFavorite = true
        updateFabIcon()
    }

    private fun isMovieFavorited(movie: Movie, callback: (Boolean) -> Unit) {
        favViewModel.isMovieFavorited(movie.id).observe(this) { isFavorited ->
            callback(isFavorited)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteMovie() {
        val favoriteMovie = FavoriteMovie(
            movie.id,
            movie.judul,
            movie.poster,
            movie.genre,
            movie.desc,
            movie.director,
            movie.rating,
            movie.tahun
        )
        favViewModel.insertFavoriteMovie(favoriteMovie)
        Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show()
        isFavorite = true
        updateFabIcon()
    }
    private fun removeFavoriteMovie() {
        movie.judul?.let { judul ->
            favViewModel.removeFavoriteMovieByJudul(judul)
            Toast.makeText(this, "Movie removed from favorites", Toast.LENGTH_SHORT).show()
            isFavorite = false
            updateFabIcon()
        }
    }
    private fun updateFabIcon() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val iconResource = if (isFavorite) R.drawable.ic_fav else R.drawable.ic_unfav
        fab.setImageResource(iconResource)

        // Set click listener for the FAB button
        fab.setOnClickListener {
            toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        if (!isFavorite) {
            insertFavoriteMovie()
        } else {
            removeFavoriteMovie()
        }
    }

    companion object{
        val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_JUDUL = "extra_judul"
    }
}

