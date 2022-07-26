package com.example.myapplication.response;

import com.example.myapplication.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;


//this is a response class

public class MovieSearchResponse {
    @SerializedName("results")
    private List<MovieModel> movieModels;

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }


}
