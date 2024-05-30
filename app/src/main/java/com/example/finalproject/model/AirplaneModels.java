package com.example.finalproject.model;

import com.google.gson.annotations.SerializedName;

public class AirplaneModels {

    @SerializedName("airline")
    private String airline;

    @SerializedName("arrival")
    private String arrival;

    @SerializedName("departure")
    private String departure;

    @SerializedName("flight")
    private String flight;

    @SerializedName("type")
    private String type;

    @SerializedName("station")
    private String station;

    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
