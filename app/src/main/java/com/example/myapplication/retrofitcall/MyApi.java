package com.example.myapplication.retrofitcall;

import com.example.myapplication.model.MovieModel;
import com.example.myapplication.response.CastResponse;
import com.example.myapplication.response.MovieSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApi {

    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

    @GET("3/movie/popular")
    Call<MovieSearchResponse> movieSearch(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("language") String language
    );

    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("3/search/movie")
    Call<MovieSearchResponse> search(
            @Query("api_key") String api_key,
            @Query("query") String nameOfMovie

    );

    //https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    @GET("3/movie/top_rated")
    Call<MovieSearchResponse> getTopRated(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    // https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=<<api_key>>&language=en-US
    @GET("3/movie/{movie_id}/credits")
    Call<CastResponse> getCast(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

}
