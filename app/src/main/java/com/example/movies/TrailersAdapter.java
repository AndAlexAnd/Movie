package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> { // переопределяем ctrl+i все методы

    private List<Trailer> trailers = new ArrayList<>(); // + setter на нее

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged(); // когда установим новые значения необходимо вызывать этот метод
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false); // Создаем ViewHolder
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer trailer = trailers.get(position); // по номеру позиции получаем трейлер
        holder.textViewTrailerName.setText(trailer.getName()); // устанавливаем текст внутрь textView

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    static class TrailersViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTrailerName;

        public TrailersViewHolder(@NonNull View itemView) { // ctrl+o
            super(itemView);

            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
        }
    }
}
