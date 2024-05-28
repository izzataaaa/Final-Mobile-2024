package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private ImageView airlineImage;
    private TextView tvDetail;
    private TextView tvAirline;
    private TextView tvArrival;
    private TextView tvDeparture;
    private TextView tvFlight;
    private TextView tvStation;
    private TextView tvType;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi komponen UI
        btnBack = findViewById(R.id.btn_back);
        airlineImage = findViewById(R.id.airline);
        tvDetail = findViewById(R.id.tvDetail);
        tvAirline = findViewById(R.id.tv_airline);
        tvArrival = findViewById(R.id.tv_arrival);
        tvDeparture = findViewById(R.id.tv_departure);
        tvFlight = findViewById(R.id.tv_flight);
        tvStation = findViewById(R.id.tv_station);
        tvType = findViewById(R.id.tv_type);
        progressBar = findViewById(R.id.progressBar1);

        // Mengatur klik tombol kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tampilkan data (misalnya, dari Intent atau sumber data lain)
        showDetails();
    }

    private void showDetails() {
        // Tampilkan ProgressBar saat memuat data
        progressBar.setVisibility(View.VISIBLE);

        // Simulasikan pemuatan data dengan waktu tunda
        // Anda dapat mengganti bagian ini dengan pemanggilan API atau pengambilan data dari database
        airlineImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah data dimuat, sembunyikan ProgressBar dan tampilkan data
                progressBar.setVisibility(View.GONE);


                // Contoh data yang ditampilkan
                tvAirline.setText("Example Airline");
                tvArrival.setText("10:00 AM");
                tvDeparture.setText("08:00 AM");
                tvFlight.setText("FL1234");
                tvStation.setText("Station A");
                tvType.setText("Type X");
            }
        }, 2000); // Waktu tunda 2 detik untuk simulasi pemuatan data
    }
}
