package com.example.finalproject.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.finalproject.R;
import com.example.finalproject.adapter.AirplaneAdapter;
import com.example.finalproject.model.AirplaneModels;
import com.example.finalproject.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SaveFragment extends Fragment {

    private AirplaneAdapter airplaneAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<AirplaneModels> airplaneModels = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstancesState) {
        super.onViewCreated(view, saveInstancesState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        context = getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        airplaneAdapter = new AirplaneAdapter(airplaneModels, context, new DatabaseHelper(context));
        recyclerView.setAdapter(airplaneAdapter);

        // Panggil AsyncTask untuk memuat data
        new LoadDataAsyncTask().execute();
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, List<AirplaneModels>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Tampilkan progress bar sebelum memulai pemuatan data
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<AirplaneModels> doInBackground(Void... voids) {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            return dbHelper.getAllAirplanes();
        }

        @Override
        protected void onPostExecute(List<AirplaneModels> airplanes) {
            super.onPostExecute(airplanes);
            if (airplanes != null) {
                airplaneModels.clear();
                airplaneModels.addAll(airplanes);
                airplaneAdapter.notifyDataSetChanged();
            }
            // Sembunyikan progress bar setelah data dimuat
            progressBar.setVisibility(View.GONE);
        }
    }
}
