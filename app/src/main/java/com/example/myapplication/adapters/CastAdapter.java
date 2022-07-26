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
import com.example.myapplication.model.CastModel;
import com.example.myapplication.util.Essentials;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    List<CastModel> castModels;
    Context context;

    public CastAdapter(List<CastModel> castModels, Context context) {
        this.castModels = castModels;
        this.context = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cast_list_item,parent,false);
        CastViewHolder castViewHolder = new CastViewHolder(view);
        return castViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        CastModel model = castModels.get(position);
        holder.textViewCastName.setText(model.getName());
        holder.textViewCastCharacter.setText(model.getCharacter());
        Glide.with(context).load(Essentials.IMAGE_CONSTANT + model.getProfile_path())
                .circleCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.imageViewCastProfile);

    }

    @Override
    public int getItemCount() {
        return 9;
    }


    class CastViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewCastProfile;
        TextView textViewCastName,textViewCastCharacter;


        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCastProfile = itemView.findViewById(R.id.imageViewCastProfile);
            textViewCastName = itemView.findViewById(R.id.textViewCastName);
            textViewCastCharacter = itemView.findViewById(R.id.textViewCastCharacter);
        }
    }

}
