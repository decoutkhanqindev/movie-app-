package com.example.movieapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.model.Film;
import com.example.movieapp.view.FilmDetailActivity;

import java.util.ArrayList;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmListViewHolder> {
    private final ArrayList<Film> filmArrayList;
    private final Context context;

    public FilmListAdapter(Context context, ArrayList<Film> filmArrayList) {
        this.context = context;
        this.filmArrayList = filmArrayList;
    }

    @NonNull
    @Override
    public FilmListAdapter.FilmListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_viewholder, parent, false);
        return new FilmListViewHolder(viewInflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.FilmListViewHolder holder, int position) {
        Film film = filmArrayList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(40));
        Glide.with(context).load(film.getPoster()).apply(requestOptions).into(holder.filmPoster);

        holder.filmTitle.setText(film.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FilmDetailActivity.class);
            intent.putExtra("Film", film);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
    }

    public static class FilmListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView filmPoster;
        private final TextView filmTitle;

        public FilmListViewHolder(@NonNull View itemView) {
            super(itemView);
            filmPoster = itemView.findViewById(R.id.filmPoster);
            filmTitle = itemView.findViewById(R.id.filmTitle);
        }
    }
}
