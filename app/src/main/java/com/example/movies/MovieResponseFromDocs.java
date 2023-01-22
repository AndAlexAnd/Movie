package com.example.movies;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponseFromDocs {
    @SerializedName("docs") // если ответ сервера не совпадает с именем нашей переменной, то правильное название указываем через @SerializedName("docs")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public MovieResponseFromDocs(List<Movie> movies) {
        this.movies = movies;
    }
}
