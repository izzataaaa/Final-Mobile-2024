package com.example.finalproject.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.finalproject.R;
import com.example.finalproject.adapter.AirplaneAdapter;
import com.example.finalproject.api.ApiConfig;
import com.example.finalproject.api.ApiService;
import com.example.finalproject.model.AirplaneModels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirplaneFragment extends Fragment {
    private ApiService apiService;
    private AirplaneAdapter airplaneAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<AirplaneModels> airplaneModels = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_airplane, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        context = getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        airplaneAdapter = new AirplaneAdapter(airplaneModels, context);
        recyclerView.setAdapter(airplaneAdapter);

        apiService = ApiConfig.getClient().create(ApiService.class);

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        progressBar.setVisibility(View.VISIBLE); // Tampilkan ProgressBar saat memuat data

        Call<List<AirplaneModels>> call = apiService.getAirlineFlights("SIA");
        call.enqueue(new Callback<List<AirplaneModels>>() {
            @Override
            public void onResponse(Call<List<AirplaneModels>> call, Response<List<AirplaneModels>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    List<AirplaneModels> airplaneModelsList = response.body();
                    if (airplaneModelsList != null) {
                        airplaneModels.addAll(airplaneModelsList);
                        airplaneAdapter.notifyDataSetChanged();
                    } else {
                        showToast("No data available");
                    }
                } else {
                    showToast("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<List<AirplaneModels>> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Sembunyikan ProgressBar jika ada kesalahan
                showToast("Failed to fetch data");
            }
        });
    }

    private void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
