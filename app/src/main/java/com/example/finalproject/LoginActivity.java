package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.sqlite.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.usernm);
        passwordEditText = findViewById(R.id.psswr);
        loginButton = findViewById(R.id.btnlogin);
        registerButton = findViewById(R.id.btnregist);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty()) {
                usernameEditText.setError("Please enter your username");
            } else if (password.isEmpty()) {
                passwordEditText.setError("Please enter your password");
            } else {
                login(username, password);
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {
        try (SQLiteDatabase db = databaseHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     databaseHelper.getTableUser(),
                     new String[]{databaseHelper.getColumnIdUser()},
                     databaseHelper.getColumnUsername() + "=? AND " + databaseHelper.getColumnPassword() + "=?",
                     new String[]{username, password},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(databaseHelper.getColumnIdUser());
                if (idColumnIndex != -1) {
                    int userId = cursor.getInt(idColumnIndex);
                    loginSuccess(userId);
                    updateLoginStatus(username, true);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginSuccess(int userId) {
        // Logic to handle successful login, like saving user ID in shared preferences
    }

    private void updateLoginStatus(String username, boolean isLoggedIn) {
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(databaseHelper.getColumnIsLoggedIn(), isLoggedIn ? 1 : 0);
            db.update(databaseHelper.getTableUser(), values, databaseHelper.getColumnUsername() + " = ?", new String[]{username});
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        try (SQLiteDatabase db = databaseHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     databaseHelper.getTableUser(),
                     new String[]{databaseHelper.getColumnIdUser()},
                     databaseHelper.getColumnIsLoggedIn() + " = ?",
                     new String[]{"1"},
                     null, null, null)) {

            if (cursor.getCount() > 0) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    }
}
