package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        MoviesAdapter moviesAdapter = new MoviesAdapter(); // который использовали уже ранее
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewMovies.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(MovieFromDocs movieFromDocs) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movieFromDocs);
                startActivity(intent);
            }
        });


        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        viewModel.getMoviesFromDocs().observe(this, new Observer<List<MovieFromDocs>>() {
            @Override
            public void onChanged(List<MovieFromDocs> movieFromDocs) {
            moviesAdapter.setMovieFromDocs(movieFromDocs);
            }
        });

    }

    public static Intent newIntent (Context context){
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}