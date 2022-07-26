package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    //model class for each movie
    //using parcelable to get data and show in details activity

    private int id;
    private String title;
    private String original_language;
    private float vote_average;
    private String poster_path;
    private String overview;
    private String backdrop_path;


    protected MovieModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        original_language = in.readString();
        vote_average = in.readFloat();
        poster_path = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
    }


    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String vote_average() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(original_language);
        parcel.writeFloat(vote_average);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(backdrop_path);
    }
}
