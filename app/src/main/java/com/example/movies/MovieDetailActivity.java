package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPosterDetail;
    private TextView textViewTitleDetail;
    private TextView textViewYearDetail;
    private TextView textViewDescriptionDetail;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;

    private TrailersAdapter trailersAdapter;
    private ReviewAdapter reviewAdapter;

    private ImageView imageViewStar;

    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";

    private MovieDetailViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        initViews();
        trailersAdapter = new TrailersAdapter();
        reviewAdapter = new ReviewAdapter();
       recyclerViewTrailers.setAdapter(trailersAdapter); // у recyclerViewTrailers устанавливаем созданный нами адаптер
        recyclerViewReviews.setAdapter(reviewAdapter);



        MovieFromDocs movieFromDocs = (MovieFromDocs) getIntent().getSerializableExtra(EXTRA_MOVIE); // получаем наш созданный интент

        Glide.with(this) // устанавливаем изображение во View
                .load(movieFromDocs.getPoster().getUrl())
                .into(imageViewPosterDetail);
        textViewTitleDetail.setText(movieFromDocs.getName()); // устанавливаем текст имя во View
        textViewYearDetail.setText(String.valueOf(movieFromDocs.getYear())); // устанавливаем текст год во View int приводим к String
        textViewDescriptionDetail.setText(movieFromDocs.getDescription()); // устанавливаем текст описание во View

        viewModel.loadTrailers(movieFromDocs.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() { // подписываемся на загруженные фильмы
            @Override
            public void onChanged(List<Trailer> trailers) {
                // устанавливаем список трейлеров в адаптер
                trailersAdapter.setTrailers(trailers);
            }
        });
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() { // навешиваем слушатель на адаптер
            @Override
            public void OnTrailerClick(Trailer trailer) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(trailer.getUrl()));
            startActivity(intent);
            }
        });

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewList) {
            reviewAdapter.setReviews(reviewList);
            }
        });
        viewModel.loadReviews(movieFromDocs.getId()); // запускаем загрузку отзывов

        Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);
        viewModel.getFavouriteMovie(movieFromDocs.getId()).observe(this, new Observer<MovieFromDocs>() {
            @Override
            public void onChanged(MovieFromDocs movieFromDocsFromDb) {

                if (movieFromDocsFromDb == null){

                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movieFromDocs);
                        }
                    });

                } else {

                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movieFromDocs.getId());
                        }
                    });
                }

            }
        });

    }



    private void initViews(){
        imageViewPosterDetail = findViewById(R.id.imageViewPosterDetail);
        textViewTitleDetail = findViewById(R.id.textViewTitleDetail);
        textViewYearDetail = findViewById(R.id.textViewYearDetail);
        textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        imageViewStar = findViewById(R.id.imageViewStar);
    }
    public static Intent newIntent(Context context, MovieFromDocs movieFromDocs){ // создаем интент для передачи содержимого Movie
        Intent intent = new Intent(context, MovieDetailActivity.class); // создаем интент для передачи содержимого Movie
        intent.putExtra(EXTRA_MOVIE, movieFromDocs); // кладем значение
        return intent; // возвращаем интент
    }
}