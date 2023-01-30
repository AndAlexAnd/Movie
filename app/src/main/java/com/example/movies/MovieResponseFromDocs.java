package com.example.movies;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponseFromDocs {
    @SerializedName("docs") // если ответ сервера не совпадает с именем нашей переменной, то правильное название указываем через @SerializedName("docs")
    private List<MovieFromDocs> movies;

    public MovieResponseFromDocs(List<MovieFromDocs> movies) {
        this.movies = movies;
    }

    public List<MovieFromDocs> getMovies() {
        return movies;
    }



    @Override
    public String toString() {
        return "MovieResponseFromDocs{" +
                "movies=" + movies +
                '}';
    }
}
