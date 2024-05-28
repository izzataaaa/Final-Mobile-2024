package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.AirplaneModels;

import java.util.List;

public class AirplaneAdapter extends RecyclerView.Adapter<AirplaneAdapter.ViewHolder> {

    private List<AirplaneModels> airplaneModels;
    private Context context;

    public AirplaneAdapter(List<AirplaneModels> airplaneModels, Context context) {
        this.airplaneModels = airplaneModels;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return airplaneModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView tv_airline, tv_arrival, tv_departure , tv_flight, tv_type, tv_station;

        public ViewHolder(View view) {
            super(view);
            tv_arrival = view.findViewById(R.id.tv_arrival);
            tv_airline = view.findViewById(R.id.tv_airline);
            tv_departure = view.findViewById(R.id.tv_departure);
            tv_flight = view.findViewById(R.id.tv_flight);
            tv_type = view.findViewById(R.id.tv_type);
            tv_station = view.findViewById(R.id.tv_station);

        }
    }
}