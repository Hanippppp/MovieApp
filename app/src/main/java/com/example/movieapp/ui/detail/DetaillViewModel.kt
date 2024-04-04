package com.example.movieapp.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.MovieRepository

class DetailViewModel(application: Application): ViewModel() {
    private val mMovieRepository: MovieRepository = MovieRepository(application)



}