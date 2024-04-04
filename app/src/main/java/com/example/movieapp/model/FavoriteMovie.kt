package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "judul")
    var judul: String? = null,

    @ColumnInfo(name = "poster")
    var poster: String? = null,

    @ColumnInfo(name = "genre")
    var genre: String? = null,

    @ColumnInfo(name = "desc")
    var desc: String? = null,

    @ColumnInfo(name = "director")
    var director: String? = null,

    @ColumnInfo(name = "rating")
    var rating: Float? = null,

    @ColumnInfo(name = "tahunRilis")
    var tahun: Int? = null,
) : Parcelable
