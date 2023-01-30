package com.example.movies;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("url")
    private String url;


    public Poster(String url) {
        this.url = url;
    }

    public String getUrl() {


        return url;
    }



    //catch (Exception e) {
    //                throw new RuntimeException(e);
    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }
}
