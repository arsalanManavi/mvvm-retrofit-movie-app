package com.example.myapplication.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.CastModel;
import com.example.myapplication.response.CastResponse;
import com.example.myapplication.response.MovieSearchResponse;
import com.example.myapplication.service.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//this class is used for sending  requests

public class MovieRepository {

    private MutableLiveData<MovieSearchResponse> movie;
    private MutableLiveData<MovieSearchResponse> topRatedMovie;
    private MutableLiveData<MovieSearchResponse> movieSearch;
    private MutableLiveData<CastResponse> cast;
    private static MovieRepository instance;

    //singleton pattern of movieRepository
    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movie = new MutableLiveData<>();
        topRatedMovie = new MutableLiveData<>();
        movieSearch = new MutableLiveData<>();
        cast = new MutableLiveData<>();
    }

    //this methode is used for send request and get response of popular movies
    public LiveData<MovieSearchResponse> getMovies(String apiKey, int page) {
        Call<MovieSearchResponse> movieSearch = RetrofitSingleton.getMyApi().movieSearch(apiKey, page, "en");
        movieSearch.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    movie.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

        return movie;
    }


    //this methode is used for send request and get response of topRated movies
    public LiveData<MovieSearchResponse> getTopRatedMovie(String apiKey, int page) {
        Call<MovieSearchResponse> topRatedMovieCall = RetrofitSingleton.getMyApi().getTopRated(apiKey, page);
        topRatedMovieCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    topRatedMovie.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
        return topRatedMovie;
    }

    //this method is used for send request and get response of searched movie
    public LiveData<MovieSearchResponse> searchMovie(String apiKey,String nameOfMovie){

        Call<MovieSearchResponse> movieSearchResponseCall = RetrofitSingleton.getMyApi().search(apiKey,nameOfMovie);
        movieSearchResponseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code()==200){
                    movieSearch.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
        return movieSearch;
    }

    //this methode is used for send request and get response of cast and crew of single  movie
    public LiveData<CastResponse> getCast(int id,String apiKey){
        Call<CastResponse> castResponseCall = RetrofitSingleton.getMyApi().getCast(id,apiKey);
        castResponseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.code() ==200){
                    cast.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
        return cast;
    }

}
