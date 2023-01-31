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

    private OnReachEndListener onReachEndListener; // + добавляем setter atl+insert

    private OnMovieClickListener onMovieClickListener; // Добавляем переменную интерфейсного типа как переменную экземпляра + setter

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        // теперь из Activity можем вызывать этот интерфейс, переопределять слушатель и определять поведение
        this.onReachEndListener = onReachEndListener;
    }

    public void setMovieFromDocs(List<MovieFromDocs> movieFromDocs) {

        this.movieFromDocs = movieFromDocs;
        notifyDataSetChanged(); // добавляем для уведомления об изменении
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // вызывается столько раз, чтобы показать, все фильмы по 4 картинки на экране, примерно 4-6 раз
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

        Log.d("MoviesAdapter", "onBindViewHolder" + position);// проверяем
        //holder.textViewRating.setText(String.format("%.1f", rating));
        //этот метод будет вызываться для каждого элемента списка
        MovieFromDocs movieFromDocsOnBind = movieFromDocs.get(position); // получаем объект MovieFromDocs
        // устанавливаем картинку при помощи Glide
               if (movieFromDocsOnBind.getPoster() != null){
                    Glide.with(holder.itemView)

                            .load(movieFromDocsOnBind.getPoster().getUrl())
                            .error(R.drawable.circle_red)
                            .placeholder(R.drawable.circle_red)
                            .fallback(R.drawable.circle_red)
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

        if (position >= movieFromDocs.size() - 10 && onReachEndListener != null) { // определяем середину списка (до конца списка осталось меньше 10 элементов
            onReachEndListener.onReachEnd();
            // стартовать загрузку из адаптера нельзя
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (onMovieClickListener != null){ // делаем проверку, если сделан клик (становится сразу !=null, то передаем объект movie
                onMovieClickListener.onMovieClick(movieFromDocsOnBind);
            }
            }
        });

    }

    interface OnMovieClickListener{ // создаем свой интерфейс слушателя клика
        void onMovieClick(MovieFromDocs movieFromDocs);
    }

    @Override
    public int getItemCount() {
        return movieFromDocs.size();
    }

    interface OnReachEndListener { // создаем callback интерфейс, для определения окончания списка
        void onReachEnd();
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
