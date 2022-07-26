package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapters.MovieSearchAdapter;
import com.example.myapplication.interfaces.OnSearchMovieClicked;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.reciever.InternetReceiver;
import com.example.myapplication.response.MovieSearchResponse;
import com.example.myapplication.util.Essentials;
import com.example.myapplication.viewmodels.MovieViewModel;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnSearchMovieClicked {

    RecyclerView recyclerViewSearch;
    MovieViewModel viewModel;
    List<MovieModel> movies;
    MovieSearchAdapter movieSearchAdapter;
    InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearchMovie);
        String nameOfMovie = getIntent().getStringExtra("query");
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        observeSearchMovie(Essentials.API_KEY, nameOfMovie);
        internetReceiver = new InternetReceiver(viewModel);
    }



    //observe searched movie and put it into recyclerView to perform it to user
    private void observeSearchMovie(String apiKey, String nameOfMovie) {

        viewModel.searchMovie(apiKey, nameOfMovie).observe(SearchActivity.this, new Observer<MovieSearchResponse>() {
            @Override
            public void onChanged(MovieSearchResponse movieSearchResponse) {
                movies = movieSearchResponse.getMovieModels();
                recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                movieSearchAdapter = new MovieSearchAdapter(SearchActivity.this, movies, SearchActivity.this::onSearchMovieClick);
                recyclerViewSearch.setAdapter(movieSearchAdapter);
            }
        });
    }

    @Override
    public void onSearchMovieClick(int position) {
        //get data from movie which clicked
        //and send data into details activity
        Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
        intent.putExtra("searchMovie", movieSearchAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(internetReceiver);
        super.onStop();
    }
}