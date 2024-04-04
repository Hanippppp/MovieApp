package com.example.movieapp.helper

import com.example.movieapp.model.FavoriteMovie
import com.example.movieapp.model.Movie

fun FavoriteMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        judul = this.judul,
        poster = this.poster,
        genre = this.genre,
        desc = this.desc,
        director = this.director,
        rating = this.rating,
        tahun = this.tahun
    )
}