package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

        loadUserData();

        btnSave.setOnClickListener(v -> {
            onSaveButtonClick();
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
                saveUserData(username, number);

                Intent resultIntent = new Intent();
                setResult(EditProfileActivity.RESULT_OK, resultIntent);
                finish();
            }
        } else {
            Toast.makeText(EditProfileActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,
                new String[]{DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PHONE},
                DatabaseHelper.COLUMN_IS_LOGGED_IN + " = ?",
                new String[]{"1"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));

            tvName.setText(name);
            tvNumber.setText(phone);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    private void saveUserData(String name, String number) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, name);
        values.put(DatabaseHelper.COLUMN_PHONE, number);

        db.update(DatabaseHelper.TABLE_USER, values, DatabaseHelper.COLUMN_IS_LOGGED_IN + " = ?", new String[]{"1"});
        db.close();
    }

//    private void EditSuccess(int userId) {
//        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("user_id", userId);
//        editor.apply();
//    }
}