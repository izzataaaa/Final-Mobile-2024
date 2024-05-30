package com.example.finalproject.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.finalproject.R;
import com.example.finalproject.adapter.AirplaneAdapter;
import com.example.finalproject.model.AirplaneModels;
import com.example.finalproject.sqlite.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        airplaneModels = new ArrayList<>();
        airplaneAdapter = new AirplaneAdapter(airplaneModels, context, dbHelper);
        recyclerView.setAdapter(airplaneAdapter);

        List<AirplaneModels> airplanes = dbHelper.getAllAirplanes();

        if (airplanes != null) {
            for (AirplaneModels airplane : airplanes) {
                String airlane = airplane.getAirline();
                String arrival = airplane.getArrival();
                String departure = airplane.getDeparture();
                String flight = airplane.getFlight();
                String type = airplane.getType();
                String station = airplane.getStation();

                airplaneModels.add(airplane);
            }
            airplaneAdapter.notifyDataSetChanged();
        }

    }
}