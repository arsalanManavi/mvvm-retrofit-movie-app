package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.adapters.CastAdapter;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.reciever.InternetReceiver;
import com.example.myapplication.response.CastResponse;
import com.example.myapplication.util.Essentials;
import com.example.myapplication.viewmodels.MovieViewModel;

public class DetailsActivity extends AppCompatActivity {
    NestedScrollView nestedScrollView;
    TextView textViewTitle, textViewOverView;
    ImageView imageViewDetails;
    RecyclerView recyclerViewCast;
    CastAdapter castAdapter;
    MovieViewModel viewModel;
    ProgressBar detailProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textViewTitle = findViewById(R.id.textViewTitleDetails);
        textViewOverView = findViewById(R.id.textViewOverViewDetails);
        imageViewDetails = findViewById(R.id.imageViewDetails);
        recyclerViewCast = findViewById(R.id.recyclerViewCast);
        detailProgressBar = findViewById(R.id.details_progressBar);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        GetDataFromIntent();


    }


    //observe the cast information and show them to user
    private void observeData(int id, String apiKey) {
        viewModel.getCast(id, apiKey).observe(DetailsActivity.this, new Observer<CastResponse>() {
            @Override
            public void onChanged(CastResponse castResponse) {
                castAdapter = new CastAdapter(castResponse.getCastModels(), DetailsActivity.this);
                recyclerViewCast.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewCast.setAdapter(castAdapter);
            }
        });

    }

    //check and get data of each intent
    //and set data into views
    private void GetDataFromIntent() {

        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            textViewTitle.setText(movieModel.getTitle());
            textViewOverView.setText(movieModel.getOverview());
            Glide.with(this).load(Essentials.IMAGE_CONSTANT2 + movieModel.getBackdrop_path())
                    .placeholder(R.drawable.place_holder)
                    .into(imageViewDetails);
            observeData(movieModel.getId(), Essentials.API_KEY);
        }

        if (getIntent().hasExtra("topRatedMovie")) {
            MovieModel model = getIntent().getParcelableExtra("topRatedMovie");
            textViewTitle.setText(model.getTitle());
            textViewOverView.setText(model.getOverview());
            Glide.with(this).load(Essentials.IMAGE_CONSTANT2 + model.getBackdrop_path())
                    .placeholder(R.drawable.place_holder)
                    .into(imageViewDetails);
            observeData(model.getId(), Essentials.API_KEY);
        }

        if (getIntent().hasExtra("searchMovie")) {
            MovieModel searchModel = getIntent().getParcelableExtra("searchMovie");
            textViewTitle.setText(searchModel.getTitle());
            textViewOverView.setText(searchModel.getOverview());
            Glide.with(this).load(Essentials.IMAGE_CONSTANT2 + searchModel.getBackdrop_path())
                    .placeholder(R.drawable.place_holder)
                    .into(imageViewDetails);
            observeData(searchModel.getId(), Essentials.API_KEY);
        }

    }


}