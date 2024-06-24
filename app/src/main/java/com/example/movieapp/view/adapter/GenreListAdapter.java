package com.example.movieapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.Film;

import java.util.ArrayList;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.GenreViewHolder> {
    private Context context;
    private ArrayList<String> genreArrList;

    public GenreListAdapter(Context context, ArrayList<String> genreArrList) {
        this.context = context;
        this.genreArrList = genreArrList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_viewholder, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = genreArrList.get(position);
        holder.genre.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genreArrList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder{
        private TextView  genre;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.filmDetailGenre);
        }
    }

}
