package com.example.movieapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FavActivityBinding
import com.example.movieapp.helper.toMovie
import com.example.movieapp.model.FavoriteMovie
import com.google.android.material.appbar.MaterialToolbar



class FavActivity: AppCompatActivity(), FavoriteMovieAdapter.OnItemClickListener {

    private lateinit var favViewModel: FavViewModel
    private lateinit var binding: FavActivityBinding
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar2.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        favoriteMovieAdapter = FavoriteMovieAdapter(this, emptyList(), this)
        binding.rvMovie.adapter = favoriteMovieAdapter

        favViewModel = obtainViewModel(this@FavActivity)

        observeFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        favViewModel.getAllFavoriteMovies().observe(this) { favoriteMovies ->
            favoriteMovieAdapter.updateFavoriteMovies(favoriteMovies)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

    override fun onItemClick(favoriteMovie: FavoriteMovie) {
        val movie = favoriteMovie.toMovie()
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_MOVIE, movie)
        }
        startActivity(intent)
    }
    companion object {

    }

}