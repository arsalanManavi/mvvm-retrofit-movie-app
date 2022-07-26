package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.OnTopRatedMovieClicked;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.util.Essentials;

import java.util.List;

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder> {


    Context context;
    List<MovieModel> models;
    OnTopRatedMovieClicked onTopRatedMovieClicked;

    public TopRatedMovieAdapter(Context context,List<MovieModel> models,OnTopRatedMovieClicked onTopRatedMovieClicked) {
        this.context = context;
        this.models = models;
        this.onTopRatedMovieClicked = onTopRatedMovieClicked;
    }



    @NonNull
    @Override
    public TopRatedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_rated_item_list,parent,false);
        TopRatedMovieViewHolder movieViewHolder = new TopRatedMovieViewHolder(view,onTopRatedMovieClicked);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMovieViewHolder holder, int position) {
        MovieModel movieModel = models.get(position);
            holder.titleTopRated.setText(movieModel.getTitle());
        Glide.with(context).load(Essentials.IMAGE_CONSTANT + movieModel.getPoster_path())
                .placeholder(R.drawable.place_holder)
                .into(holder.imageViewTopRated);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }




    class TopRatedMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView imageViewTopRated;
        TextView titleTopRated;
        OnTopRatedMovieClicked onTopRatedMovieClicked;

        public TopRatedMovieViewHolder(@NonNull View itemView,OnTopRatedMovieClicked onTopRatedMovieClicked) {
            super(itemView);
            this.onTopRatedMovieClicked = onTopRatedMovieClicked;
            imageViewTopRated = itemView.findViewById(R.id.imageViewTopRated);
            titleTopRated = itemView.findViewById(R.id.textviewTitleTopRated);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onTopRatedMovieClicked.onTopRatedMovieClick(getAdapterPosition());
        }
    }


    public MovieModel getPosition(int position){
        if (models != null){
            if (models.size()>0){
                return models.get(position);
            }
        }
        return null;
    }

}
