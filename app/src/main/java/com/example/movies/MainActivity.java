package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private final static String TAG = "MainActivity";

    private RecyclerView recyclerViewMovies; // создаем ссылку на RecyclerView
    private MoviesAdapter moviesAdapter; // и создаем ссылку на MoviesAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter); // устанавливаем адаптер в recyclerView
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2)); // устанавливаем табличный вид в 2 колонки
        // P7VS855-8QM4V7B-JQW4RN1-XK8Q243 token kinopoisk personal my
        // https://api.kinopoisk.dev/movie?token=P7VS855-8QM4V7B-JQW4RN1-XK8Q243&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&page=2&limit=40
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getMoviesLiveData().observe(this, new Observer<List<MovieFromDocs>>() {
            @Override
            public void onChanged(List<MovieFromDocs> movieFromDocs) {
                // фильмы в адаптер добавляем в onChanged, когда они будут загружены из сети

                moviesAdapter.setMovieFromDocs(movieFromDocs);
            }
        });
        viewModel.loadMovies();
    }
}