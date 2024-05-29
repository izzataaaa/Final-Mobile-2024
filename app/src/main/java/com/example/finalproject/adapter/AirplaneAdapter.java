package com.example.finalproject.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.RegisterActivity;
import com.example.finalproject.model.AirplaneModels;
import com.example.finalproject.sqlite.DatabaseHelper;

import java.util.List;
import android.widget.Toast;


public class AirplaneAdapter extends RecyclerView.Adapter<AirplaneAdapter.ViewHolder> {

    private List<AirplaneModels> airplaneModels;
    private Context context;
    DatabaseHelper dbHelper;
    private AdapterView.OnItemClickListener listener;

    // Interface untuk menangkap tindakan klik pada item
    public interface OnItemClickListener {
        void onItemClick(AirplaneModels airplaneModels1);
    }

    // Metode untuk menetapkan listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = (AdapterView.OnItemClickListener) listener;
    }

    public AirplaneAdapter(List<AirplaneModels> airplaneModels, Context context, DatabaseHelper dbHelper) {
        this.airplaneModels = airplaneModels;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AirplaneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirplaneAdapter.ViewHolder holder, int position) {
       AirplaneModels airplaneModels1 = airplaneModels.get(position);
       holder.tv_airline.setText(airplaneModels1.getAirline());
       holder.tv_arrival.setText(airplaneModels1.getArrival());
       holder.tv_departure.setText(airplaneModels1.getDeparture());
       holder.tv_flight.setText(airplaneModels1.getFlight());
       holder.tv_type.setText(airplaneModels1.getType());
       holder.tv_station.setText(airplaneModels1.getStation());

       holder.btn_save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (dbHelper != null) {
                   dbHelper.addAirplane(airplaneModels1);
                   Toast.makeText(context, "Airplane saved successfully", Toast.LENGTH_SHORT).show();
               } else {
                   Log.e("AirplaneAdapter", "DatabaseHelper is null");
                   Toast.makeText(context, "Error: DatabaseHelper is null", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return airplaneModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView tv_airline, tv_arrival, tv_departure , tv_flight, tv_type, tv_station;
       private  ImageView btn_save;

        public ViewHolder(View view) {
            super(view);
            tv_arrival = view.findViewById(R.id.tv_arrival);
            tv_airline = view.findViewById(R.id.tv_airline);
            tv_departure = view.findViewById(R.id.tv_departure);
            tv_flight = view.findViewById(R.id.tv_flight);
            tv_type = view.findViewById(R.id.tv_type);
            tv_station = view.findViewById(R.id.tv_station);
            btn_save = view.findViewById(R.id.btn_save);

        }
    }
}