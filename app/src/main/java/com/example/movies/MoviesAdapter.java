package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    //потом CTRL+I - переопределяем 3 метода

    private List<MovieFromDocs> movieFromDocs = new ArrayList<>(); // и добавляем сеттер на это поле



    public void setMovieFromDocs(List<MovieFromDocs> movieFromDocs) {
        this.movieFromDocs = movieFromDocs;
        notifyDataSetChanged(); // добавляем для уведомления об изменении
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // тут создаем View из макета
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        //этот метод будет вызываться для каждого элемента списка
        MovieFromDocs movieFromDocsOnBind = movieFromDocs.get(position); // получаем объект MovieFromDocs
        // устанавливаем картинку при помощи Glide
        Glide.with(holder.itemView)
                .load(movieFromDocsOnBind.getPoster().getUrl())
                .into(holder.imageViewPoster);
        // получаем рейтинг

        holder.textViewRating.setText(movieFromDocsOnBind.getRating().getKp());
    }

    @Override
    public int getItemCount() {
        return movieFromDocs.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{
        // сами внутри создаем класс MovieViewHolder и наследуемся от RecyclerView.ViewHolder
        private ImageView imageViewPoster;
        private TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) { // ALT+INSERT переопределяем конструктор
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
