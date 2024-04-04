package com.example.movieapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.detail.DetailActivity
import com.example.movieapp.ui.detail.FavActivity
import com.example.movieapp.ui.detail.FavViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(),
    MovieAdapter.FireBaseDataListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var db: FirebaseDatabase
    private lateinit var movieRef: DatabaseReference
    private lateinit var listMovie: ArrayList<Movie>
    private lateinit var mToolbar: MaterialToolbar
    private lateinit var favViewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mToolbar = binding.toolbar2
        setSupportActionBar(mToolbar)

        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.setHasFixedSize(true)

        db = FirebaseDatabase.getInstance()
        movieRef = db.reference.child(MOVIE_CHILD)

        favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        movieRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMovie = ArrayList()
                for (movieSnap in snapshot.children) {
                    movieSnap.getValue(Movie::class.java)?.let { movie ->
                        movie.judul = movieSnap.key.toString()
                        listMovie.add(movie)
                    }
                }
                adapter = MovieAdapter(this@MainActivity, listMovie)
                binding.rvMovie.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("${error.details} ${error.message}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)

        mToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val menuItemSearch = menu?.findItem(R.id.search)
                    val searchView: SearchView = menuItemSearch?.actionView as SearchView
                    searchView.queryHint = "Cari Film"
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            searchMovie(newText)
                            return true
                        }
                    })
                    true
                }

                R.id.favorite -> {
                    val intent = Intent(this, FavActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
        return true
    }


    private fun searchMovie(keyword: String?) {
        if (keyword != null) {
            val searchedMovie = listMovie.filter {
                it.judul?.contains(keyword, true) == true
                        || it.genre?.contains(keyword, true) == true
            }
            if (searchedMovie.isEmpty()) {
                showToast(getString(R.string.judul))
            } else {
                adapter.setSearchedList(searchedMovie as ArrayList<Movie>)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onDataClick(movie: Movie?) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_MOVIE, movie)
            putExtra(DetailActivity.EXTRA_JUDUL, movie?.judul)
        }
        startActivity(intent)
    }

    companion object {
        const val MOVIE_CHILD = "Film"
    }
}