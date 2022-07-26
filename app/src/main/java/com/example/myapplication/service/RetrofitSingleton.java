package com.example.myapplication.service;

import com.example.myapplication.retrofitcall.MyApi;
import com.example.myapplication.util.Essentials;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    //singleton pattern retrofit


    private static retrofit2.Retrofit.Builder retrofitBuilder = new retrofit2.Retrofit.Builder()
            .baseUrl(Essentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static retrofit2.Retrofit retrofit = retrofitBuilder.build();

    private static MyApi myApi = retrofit.create(MyApi.class);

    //get api interface
    public static MyApi getMyApi() {
        return myApi;
    }
}