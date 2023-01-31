package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPosterDetail;
    private TextView textViewTitleDetail;
    private TextView textViewYearDetail;
    private TextView textViewDescriptionDetail;

    private static final String EXTRA_MOVIE = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();

        MovieFromDocs movieFromDocs = (MovieFromDocs) getIntent().getSerializableExtra(EXTRA_MOVIE); // получаем наш созданный интент

        Glide.with(this) // устанавливаем изображение во View
                .load(movieFromDocs.getPoster().getUrl())
                .into(imageViewPosterDetail);
        textViewTitleDetail.setText(movieFromDocs.getName()); // устанавливаем текст имя во View
        textViewYearDetail.setText(String.valueOf(movieFromDocs.getYear())); // устанавливаем текст год во View int приводим к String
        textViewDescriptionDetail.setText(movieFromDocs.getDescription()); // устанавливаем текст описание во View
    }

    private void initViews(){
        imageViewPosterDetail = findViewById(R.id.imageViewPosterDetail);
        textViewTitleDetail = findViewById(R.id.textViewTitleDetail);
        textViewYearDetail = findViewById(R.id.textViewYearDetail);
        textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
    }
    public static Intent newIntent(Context context, MovieFromDocs movieFromDocs){ // создаем интент для передачи содержимого Movie
        Intent intent = new Intent(context, MovieDetailActivity.class); // создаем интент для передачи содержимого Movie
        intent.putExtra(EXTRA_MOVIE, movieFromDocs); // кладем значение
        return intent; // возвращаем интент
    }
}