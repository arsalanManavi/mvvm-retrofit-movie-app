package com.example.myapplication.viewmodels;


import android.service.carrier.CarrierService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.MovieModel;
import com.example.myapplication.repository.MovieRepository;
import com.example.myapplication.response.CastResponse;
import com.example.myapplication.response.MovieSearchResponse;

import java.util.List;

public class MovieViewModel extends ViewModel {



    MovieRepository repository;

    public MovieViewModel(){
        repository = MovieRepository.getInstance();
    }

    public LiveData<MovieSearchResponse> getMovies(String apiKey,int page) {
        return repository.getMovies(apiKey, page);

    }

    public LiveData<MovieSearchResponse> getTopRatedMovies(String apiKey,int page){

        return repository.getTopRatedMovie(apiKey,page);
    }

    public LiveData<MovieSearchResponse> searchMovie(String apiKey, String nameOfMovie){
        return repository.searchMovie(apiKey,nameOfMovie);
    }

    public LiveData<CastResponse> getCast(int movieId, String apiKey){
        return repository.getCast(movieId,apiKey);
    }


}
