package com.example.newsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.widget.Toast;

import com.example.newsproject.parametr.Articles;
import com.example.newsproject.parametr.HeadLines;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    final String API_KEY = "5f249efa37ec475f989920f8ffefa7fb";

    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountry();
        fetchJSON(country, API_KEY);
    }

    private void fetchJSON(String country, String api_key) {
        Call<HeadLines> call = Client.getInstance().getApi().getHeadLines(country, api_key);
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if(response.isSuccessful() && response.body().getArticles()!= null){
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT);
            }
        });
    }

    private String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}