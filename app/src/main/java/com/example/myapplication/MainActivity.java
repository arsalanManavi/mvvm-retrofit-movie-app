package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.MovieAdapter;
import com.example.myapplication.interfaces.OnMovieClicked;
import com.example.myapplication.interfaces.OnSearchMovieClicked;
import com.example.myapplication.interfaces.OnTopRatedMovieClicked;
import com.example.myapplication.adapters.TopRatedMovieAdapter;
import com.example.myapplication.reciever.InternetReceiver;
import com.example.myapplication.response.CastResponse;
import com.example.myapplication.response.MovieSearchResponse;
import com.example.myapplication.util.Connection;
import com.example.myapplication.util.Essentials;
import com.example.myapplication.viewmodels.MovieViewModel;

public class MainActivity extends AppCompatActivity implements OnMovieClicked, OnTopRatedMovieClicked {

    RecyclerView recyclerView, recyclerViewTopRated;
    MovieAdapter adapter;
    TopRatedMovieAdapter topRatedMovieAdapter;
    MovieViewModel viewModel;
    SearchView searchView;
    TextView textViewPopular;
    TextView textViewTopRate;
    ProgressBar progressBar;
    InternetReceiver internetReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPopular = findViewById(R.id.textViewPopular);
        textViewTopRate = findViewById(R.id.textViewTopRate);
        searchView = findViewById(R.id.searchView);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerViewTopRated = findViewById(R.id.topRatedRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        internetReceiver = new InternetReceiver(viewModel);


        observe(Essentials.API_KEY, 1);
        observeTopRatedMovie(Essentials.API_KEY, 1);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(query);
                Log.d("Tag", "onQueryTextSubmit: " + query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }


    //this method observe the popular movie and put the data into recyclerView to show to user
    private void observe(String apiKey, int page) {

        viewModel.getMovies(apiKey, page).observe(MainActivity.this, new Observer<MovieSearchResponse>() {
            @Override
            public void onChanged(MovieSearchResponse movieSearchResponse) {

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new MovieAdapter(MainActivity.this, movieSearchResponse.getMovieModels(), MainActivity.this::onMovieClick);
                recyclerView.setAdapter(adapter);

                    if (movieSearchResponse.getMovieModels().size()<=0){
                        textViewPopular.setVisibility(View.INVISIBLE);
                        textViewTopRate.setVisibility(View.GONE);
                        searchView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        recyclerViewTopRated.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    }else {
                        textViewPopular.setVisibility(View.VISIBLE);
                        textViewTopRate.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerViewTopRated.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }



            }
        });

    }

    //this method observe topRated movies and put the data into recyclerView to show to user
    private void observeTopRatedMovie(String apiKey, int page) {
        viewModel.getTopRatedMovies(apiKey, page).observe(MainActivity.this, new Observer<MovieSearchResponse>() {
            @Override
            public void onChanged(MovieSearchResponse movieSearchResponse) {
                topRatedMovieAdapter = new TopRatedMovieAdapter(MainActivity.this, movieSearchResponse.getMovieModels(), MainActivity.this::onTopRatedMovieClick);
                recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewTopRated.setAdapter(topRatedMovieAdapter);
            }
        });
    }


    //this method is obvious
    //but i leave a comment for it
    //this method get name of searched movie and start the activity that shows the results
    private void startActivity(String nameOfMovie) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("query", nameOfMovie);
        startActivity(intent);
    }


    //this method handled the movie which clicked
    //and get data of the movie to send to the details activity
    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("movie", adapter.getMoviePosition(position));
        startActivity(intent);


    }


    //this method handled the movie which clicked
    //and get data of the movie to send to the details activity
    @Override
    public void onTopRatedMovieClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("topRatedMovie", topRatedMovieAdapter.getPosition(position));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
       //register the receiver when the internet connection lost or connect
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(internetReceiver);
        super.onStop();
    }
}