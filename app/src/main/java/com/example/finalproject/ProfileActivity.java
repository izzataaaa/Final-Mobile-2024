package com.example.finalproject;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private View editregis;
    private View editlogin;
    private View tv_username;
    private View tv_password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_username = findViewById(R.id.usernm);
        tv_password = findViewById(R.id.psswr);
        editlogin = findViewById(R.id.btnlogin);
        editregis = findViewById(R.id.btnregist);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = new Intent(ProfileActivity.this, AirplaneActivity.class);
        startActivity(intent);

        // Sembunyikan ImageView dan TextView sementara menampilkan ProgressBar
        tv_username.setVisibility(View.GONE);
        tv_password.setVisibility(View.GONE);
        editlogin.setVisibility(View.GONE);
        editregis.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        // Simulasikan penundaan untuk menunjukkan proses pemrosesan data
        new Thread(() -> {
            try {
                // Menunggu selama 2 detik
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Setelah penundaan selesai, jalankan kode yang diperlukan di UI thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Tampilkan ImageView dan TextView, serta sembunyikan ProgressBar
                    tv_username.setVisibility(View.VISIBLE);
                    tv_password.setVisibility(View.VISIBLE);
                    editlogin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }).start();
    }
}