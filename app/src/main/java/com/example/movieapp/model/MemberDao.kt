package com.example.movieapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(memberModel: FavoriteMovie)

    @Query("DELETE FROM FavoriteMovie WHERE judul = :judul")
    fun delete(judul: String)

    @Query("SELECT * FROM FavoriteMovie WHERE judul = :judul")
    fun getFavoriteMovieByJudul(judul: String): LiveData<FavoriteMovie?>

    @Query("SELECT * FROM FavoriteMovie")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT EXISTS (SELECT 1 FROM FavoriteMovie WHERE id = :movieId LIMIT 1)")
    fun isMovieFavorited(movieId: Int): LiveData<Boolean>
    }
