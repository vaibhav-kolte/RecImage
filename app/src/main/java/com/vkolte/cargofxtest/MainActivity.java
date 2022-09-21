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

import com.vkolte.cargofxtest.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    ImagesInterface service;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/") //https://api.thecatapi.com/v1/images/search?limit=100&page=11&order=Desc
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ImagesInterface.class);

        callApi(page);

        binding.text1.setOnClickListener(v -> {
            page = page - 1;
            callApi(page);
        });

        binding.text5.setOnClickListener(v -> {
            page = page + 1;
            callApi(page);
        });

    }

    private void callApi(int page) {

        try {
            binding.progressBar.setVisibility(View.VISIBLE);
            Call<List<Images>> call = service.getImages("5", String.valueOf(page), "Desc");

            call.enqueue(new Callback<List<Images>>() {
                @Override
                public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                    if (response.isSuccessful()) {
                        List<Images> imagesList = response.body();
                        parseRecyclerView(imagesList);
                    } else {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.showError.setVisibility(View.VISIBLE);
                        binding.showError.setText("Something went Wrong : " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Images>> call, Throwable t) {
                    try {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.showError.setVisibility(View.VISIBLE);
                        binding.showError.setText(t.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "callApi: Exception : " + e.getMessage());
        }
    }

    private void parseRecyclerView(List<Images> imagesList) {

        try {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            binding.showError.setVisibility(View.GONE);
            binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            binding.recyclerView.setAdapter(new ImageAdapter(MainActivity.this, imagesList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}