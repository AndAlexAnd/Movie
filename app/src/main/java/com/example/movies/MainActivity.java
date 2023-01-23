package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // P7VS855-8QM4V7B-JQW4RN1-XK8Q243 token kinopoisk personal my
        // https://api.kinopoisk.dev/movie?token=P7VS855-8QM4V7B-JQW4RN1-XK8Q243&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&page=2&limit=40
        ApiFactory.apiService.loadMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponseFromDocs>() {
                    @Override
                    public void accept(MovieResponseFromDocs movieResponseFromDocs) throws Throwable {
                        Log.d("MainActivity", movieResponseFromDocs.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("MainActivity", throwable.toString());
                    }
                });
    }
}