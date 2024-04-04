package com.example.movieapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movieapp.model.FavoriteMovie
import com.example.movieapp.model.MovieRoomDatabase
import com.example.movieapp.repository.MovieRepository

class FavViewModel (application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(application)
    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        repository.insertFavoriteMovie(favoriteMovie)
    }
    fun removeFavoriteMovieByJudul(judul: String) {
        MovieRoomDatabase.databaseWriteExecutor.execute {
            repository.removeFavoriteMovieByJudul(judul)
        }
    }
    fun getFavoriteMovieByJudul(judul: String): LiveData<FavoriteMovie?> {
        return repository.getFavoriteMovieByJudul(judul)
    }
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return repository.getAllFavoriteMovies()
    }
    fun isMovieFavorited(movieId: Int): LiveData<Boolean> {
        return repository.isMovieFavorited(movieId)
    }
}

