package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private final static String TYPE_POSITIVE = "Позитивный";
    private final static String TYPE_NEGATIVE = "Негативный";
    private final static String TYPE_NEUTRAL = "Нейтральный";

    private List<Review> reviews = new ArrayList<>(); // добавляем ссылку на коллекцию в адаптер

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged(); // добавляем уведомление при изменении
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,
                parent,
                false
                );
                //создаем View из макета
        return new ReviewViewHolder(view); //создаем экземпляр View holder_а и возвращаем его
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        // получаем Review по номеру позиции
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());
        // устанавливаем тексты в textView

        String type = review.getType();
        // получаем тип
        int colorResId = android.R.color.holo_red_light;
        // получаем id цвета
        switch (type) {
            case TYPE_POSITIVE:
                colorResId = android.R.color.holo_green_light;
                break;
            case TYPE_NEUTRAL:
                colorResId = android.R.color.holo_orange_light;
                break;
        }
        // добавляем логику установки цвета фона
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        // получаем сам цвет для установки в LinearLayout
        holder.linearLayoutReview.setBackgroundColor(color);
        // устанавливаем цвет в LinearLayout
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewAuthor;
        private final TextView textViewReview;
        private final LinearLayout linearLayoutReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);;
            linearLayoutReview = itemView.findViewById(R.id.linearLayoutReview);;
        }
    }
}
