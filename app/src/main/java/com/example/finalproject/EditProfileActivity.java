package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.sqlite.DatabaseHelper;

public class EditProfileActivity extends AppCompatActivity {

    private TextView tvProfile, tvWelcome;
    private EditText tvName, tvNumber;
    private ImageView imgLogo;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private int recordId;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
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

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        recordId = getIntent().getIntExtra("USER_ID", -1);

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
                tvName.setError("Username already exists");
                Toast.makeText(EditProfileActivity.this, "Username already exists. Please use a different username.", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.insertUser(username, number);
                Toast.makeText(EditProfileActivity.this, "Update Profile successful", Toast.LENGTH_SHORT).show();
                EditSuccess(recordId); // Pass the actual userId here
                updateEditStatus(username, true);

                Intent resultIntent = new Intent();
                setResult(EditProfileActivity.RESULT_OK, resultIntent);
                finish();
            }
        } else {
            Toast.makeText(EditProfileActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void EditSuccess(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    private void updateEditStatus(String username, boolean isLoggedIn) {
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(databaseHelper.getColumnIsLoggedIn(), isLoggedIn ? 1 : 0);
            db.update(databaseHelper.getTableUser(), values, databaseHelper.getColumnUsername() + " = ?", new String[]{username});
        }
    }
}
