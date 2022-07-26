package com.example.myapplication.response;

import com.example.myapplication.model.CastModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {

    @SerializedName("cast")
    private List<CastModel> castModels;

    public List<CastModel> getCastModels() {
        return castModels;
    }
}
