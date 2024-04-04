package com.example.movieapp.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movieapp.model.FavoriteMovie
import com.example.movieapp.model.MemberDao
import com.example.movieapp.model.MovieRoomDatabase

class MovieRepository (application: Application) {
    private val memberDao: MemberDao = MovieRoomDatabase.getDatabase(application).memberDao()



    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        MovieRoomDatabase.databaseWriteExecutor.execute {
            memberDao.insert(favoriteMovie)
        }
    }

    fun removeFavoriteMovieByJudul(judul: String) {
        Log.d(TAG, "Removing favorite movie with title: $judul")
        MovieRoomDatabase.databaseWriteExecutor.execute {
            memberDao.delete(judul)
        }
    }
    fun getFavoriteMovieByJudul(judul: String): LiveData<FavoriteMovie?> {
            return memberDao.getFavoriteMovieByJudul(judul)
    }
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return memberDao.getAllFavoriteMovies()
    }
    fun isMovieFavorited(movieId: Int): LiveData<Boolean> {
        return memberDao.isMovieFavorited(movieId)
    }

    companion object {
        const val MOVIE_CHILD = "Film"
    }


}