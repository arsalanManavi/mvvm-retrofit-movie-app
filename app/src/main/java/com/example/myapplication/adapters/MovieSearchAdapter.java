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
import com.example.myapplication.interfaces.OnSearchMovieClicked;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.util.Essentials;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder> {

    Context context;
    List<MovieModel> movies;
    OnSearchMovieClicked onSearchMovieClicked;


    public MovieSearchAdapter(Context context, List<MovieModel> movies,OnSearchMovieClicked onSearchMovieClicked) {
        this.context = context;
        this.movies = movies;
        this.onSearchMovieClicked = onSearchMovieClicked;
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item_list,parent,false);
        MovieSearchViewHolder movieSearchViewHolder = new MovieSearchViewHolder(view,onSearchMovieClicked);
        return movieSearchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MovieModel model = movies.get(position);

        Glide.with(context).load(Essentials.IMAGE_CONSTANT2 + model.getPoster_path()).placeholder(R.drawable.place_holder)
                .into(holder.imageViewSearch);
        holder.textViewTitleSearch.setText(model.getTitle());
        holder.textViewLanguageSearch.setText(model.getOriginal_language());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageViewSearch;
        TextView textViewTitleSearch, textViewLanguageSearch;
        ConstraintLayout searchConstraint;
        OnSearchMovieClicked onSearchMovieClicked;
        public MovieSearchViewHolder(@NonNull View itemView,OnSearchMovieClicked onSearchMovieClicked) {
            super(itemView);
            this.onSearchMovieClicked = onSearchMovieClicked;
            imageViewSearch = itemView.findViewById(R.id.imageViewSearch);
            textViewTitleSearch = itemView.findViewById(R.id.textviewTitleSearch);
            textViewLanguageSearch = itemView.findViewById(R.id.textviewLanguageSearch);
            searchConstraint = itemView.findViewById(R.id.search_constraint);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSearchMovieClicked.onSearchMovieClick(getAdapterPosition());
        }
    }

    public MovieModel getSelectedMovie(int position){

        if (movies !=null){
            if (movies.size()>0){
                return movies.get(position);
            }
        }
        return null;

    }

}
