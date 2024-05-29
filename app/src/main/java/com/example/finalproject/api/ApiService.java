package com.example.finalproject.api;

import com.example.finalproject.model.AirplaneModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    String RAPID_API_KEY = "ddff2106bcmsh68cd9ba81958a12p1a1c99jsnee63332892de";
    String RAPID_API_HOST = "flight-data4.p.rapidapi.com";

    @Headers({
            "X-RapidAPI-Key: " + RAPID_API_KEY,
            "X-RapidAPI-Host: " + RAPID_API_HOST
    })
    @GET("get_airline_flights")
    Call<List<AirplaneModels>> getAirlineFlights(@Query("airline") String airline);
}