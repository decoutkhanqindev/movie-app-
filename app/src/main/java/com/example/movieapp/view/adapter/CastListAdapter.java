package com.example.movieapp.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.model.Cast;

import java.util.ArrayList;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.CastViewHolder> {
    private final Context context;
    private final ArrayList<Cast> castArrayList;

    public CastListAdapter(Context context, ArrayList<Cast> castArrayList) {
        this.context = context;
        this.castArrayList = castArrayList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_viewholder, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast cast = castArrayList.get(position);

        Glide.with(context).load(cast.getPicUrl()).into(holder.castImg);

        holder.castName.setText(cast.getActor());
    }

    @Override
    public int getItemCount() {
        return castArrayList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder{
        private final ImageView castImg;
        private final TextView castName;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImg = itemView.findViewById(R.id.castImg);
            castName = itemView.findViewById(R.id.castName);
        }
    }
}
