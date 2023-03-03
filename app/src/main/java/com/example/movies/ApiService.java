package com.example.movies;

import android.graphics.Movie;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=J2Z4RW3-MNQM7SN-JP98CN4-KAW796P&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=30")
   // @GET("movie?token=P7VS855-8QM4V7B-JQW4RN1-XK8Q243&field=rating.kp&search=4-8&sortField=votes.kp&sortType=1&limit=40")
    Single<MovieResponseFromDocs> loadMovies(@Query("page") int page); // Retrofit сам поставит знак амперсанда & и = к слову page
    @GET("movie?token=J2Z4RW3-MNQM7SN-JP98CN4-KAW796P&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id); // Retrofit сам поставит знак амперсанда & и = к слову id

    @GET("review?token=J2Z4RW3-MNQM7SN-JP98CN4-KAW796P&field=movieId")
    Single<ReviewResponse> loadReviews (@Query("search") int id); // Retrofit сам поставит знак амперсанда & и = к слову id
}

   // @GET("movie?token=P7VS855-8QM4V7B-JQW4RN1-XK8Q243&field=id")
    // Single<TrailerResponse> loadTrailers(@Query("search") int id); // Retrofit сам поставит знак амперсанда & и = к слову id