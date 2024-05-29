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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        context = getContext();

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        airplaneAdapter = new AirplaneAdapter(airplaneModels, context, dbHelper);
        recyclerView.setAdapter(airplaneAdapter);


        Cursor cursor;
        cursor = dbHelper.getAllAirplanes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String airlane = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnAirline()));
                String arrival = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnArrival()));
                String departure = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnDeparture()));
                String flight = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnFlight()));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnType()));
                String station = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnStation()));
                long createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(dbHelper.getColumnUsername()));
                long updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow(dbHelper.getColumnPassword()));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String createdAtStr = sdf.format(new Date(createdAt));
                String updatedAtStr = sdf.format(new Date(updatedAt));

                String timestamp = "Created at " + createdAtStr;
                if (createdAt != updatedAt) {
                    timestamp = "Updated at " + updatedAtStr;
                }
                airplaneAdapter = new AirplaneAdapter(airplaneModels, context, dbHelper);
            } while (cursor.moveToNext());
            cursor.close();
        }


    }
}