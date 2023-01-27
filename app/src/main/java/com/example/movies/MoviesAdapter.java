package com.example.movies;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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


        //holder.textViewRating.setText(String.format("%.1f", rating));
        //этот метод будет вызываться для каждого элемента списка
        MovieFromDocs movieFromDocsOnBind = movieFromDocs.get(position); // получаем объект MovieFromDocs
        // устанавливаем картинку при помощи Glide
                if (movieFromDocsOnBind.getPoster() != null){
                    Glide.with(holder.itemView)

                            .load(movieFromDocsOnBind.getPoster().getUrl())
                            .error(R.color.purple_200)
                            .placeholder(R.color.purple_200)
                            .fallback(R.color.purple_200)

                            .into(holder.imageViewPoster);
                }



        // получаем рейтинг
        double rating = movieFromDocsOnBind.getRating().getKp();
        int backgroundId; // условия установки
        if (rating > 7) {
            backgroundId = R.drawable.circle_green;
        } else if (rating > 5) {
            backgroundId = R.drawable.circle_orange;
        } else {
            backgroundId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId); // Получаем сам фон в виде объекта Drawable
        holder.textViewRating.setBackground(background);// устанавливаем сам фон у textView
        holder.textViewRating.setText(String.format("%.1f", rating));

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
