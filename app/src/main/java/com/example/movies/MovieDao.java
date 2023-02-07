package com.example.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<MovieFromDocs>> getAllFavouriteMovies(); // получение всех избранных фильмов

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    LiveData<MovieFromDocs> getFavourite(int movieId); // получение одного избранного фильма по звездочке

    @Insert
    Completable insertMovie(MovieFromDocs movieFromDocs); // добавляем фильм в БД избранных

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    Completable removeMovie(int movieId); // удаляем фильм из БД избранных


}
