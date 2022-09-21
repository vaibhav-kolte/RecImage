package com.vkolte.cargofxtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ImagesInterface service;
    TextView textView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.showError);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/") //https://api.thecatapi.com/v1/images/search?limit=100&page=11&order=Desc
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ImagesInterface.class);

        callApi();

    }

    private void callApi() {

        try {
            Call<List<Images>> call = service.getImages("100","10","Desc");

            call.enqueue(new Callback<List<Images>>() {
                @Override
                public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                    if (response.isSuccessful()) {
                        List<Images> imagesList = response.body();
                        parseRecyclerView(imagesList);
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Something went Wrong : "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Images>> call, Throwable t) {
                    try{
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(t.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "callApi: Exception : "+e.getMessage());
        }
    }

    private void parseRecyclerView(List<Images> imagesList) {

        try{
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(new ImageAdapter(MainActivity.this,imagesList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}