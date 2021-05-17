package com.example.newsproject;

import com.example.newsproject.parametr.HeadLines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Interface {

    @GET("top-headlines")
    Call<HeadLines> getHeadLines(
            @Query("country") String country,
            @Query("apikey") String apikey
    );
}
