package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var judul: String? = null,
    var genre: String? = null,
    var desc: String? = null,
    var director: String? = null,
    var poster: String? = null,
    var rating: Float? = null,
    var tahun: Int? = null,

) : Parcelable
