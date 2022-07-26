package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.OnMovieClicked;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.util.Essentials;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    Context context;
    List<MovieModel> movies;
    OnMovieClicked onMovieClicked;

    public MovieAdapter(Context context, List<MovieModel> movies,OnMovieClicked onMovieClicked) {
        this.context = context;
        this.movies = movies;
        this.onMovieClicked = onMovieClicked;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view,onMovieClicked);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MovieModel model = movies.get(position);
        holder.title.setText(model.getTitle());
        Glide.with(context)
                .load(Essentials.IMAGE_CONSTANT + model.getPoster_path())
                .placeholder(R.drawable.place_holder)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView imageView;
        ConstraintLayout constraint;
        OnMovieClicked onMovieClicked;

        public MovieViewHolder(@NonNull View itemView,OnMovieClicked onMovieClicked) {
            super(itemView);
            title = itemView.findViewById(R.id.textviewTitle);
            imageView = itemView.findViewById(R.id.imageView);
            constraint = itemView.findViewById(R.id.constraint);
            this.onMovieClicked = onMovieClicked;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieClicked.onMovieClick(getAdapterPosition());
        }
    }


    public MovieModel getMoviePosition(int position){
        if (movies != null){
            if (movies.size() > 0){
                return movies.get(position);
            }
        }
        return null;
    }

}
