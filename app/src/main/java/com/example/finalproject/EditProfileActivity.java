package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.sqlite.DatabaseHelper;

public class EditProfileActivity extends AppCompatActivity {

    private TextView tvProfile, tvWelcome, tvName, tvNumber;
    private ImageView imgLogo;
    private Button btnSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        tvProfile = findViewById(R.id.tv_profile);
        tvWelcome = findViewById(R.id.tv_welcome);
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        imgLogo = findViewById(R.id.img_logo);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        tvProfile.setText("Profile Account");
        tvWelcome.setText("Hai,");
        tvName.setText("Nama Pengguna Anda");
        tvNumber.setText("Nomor Telepon Anda");

        btnSave.setOnClickListener(v -> {
            onSaveButtonClick();
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void onSaveButtonClick() {
        String username = tvName.getText().toString().trim();
        String number = tvNumber.getText().toString().trim();

        if (!username.isEmpty() && !number.isEmpty()) {
            if (databaseHelper.isUserExists(username)) {
                tvName.setError("Username sudah ada");
                Toast.makeText(EditProfileActivity.this, "Username sudah ada. Gunakan username yang berbeda.", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.insertUser(username, number);
                Toast.makeText(EditProfileActivity.this, "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                loginSuccess(1);
                updateLoginStatus(username, true);
                finish();
            }
        } else {
            Toast.makeText(EditProfileActivity.this, "Isi semua bidang", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginSuccess(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    private void updateLoginStatus(String username, boolean isLoggedIn) {
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(databaseHelper.getColumnIsLoggedIn(), isLoggedIn ? 1 : 0);
            db.update(databaseHelper.getTableUser(), values, databaseHelper.getColumnUsername() + " = ?", new String[]{username});
        }
    }
}
