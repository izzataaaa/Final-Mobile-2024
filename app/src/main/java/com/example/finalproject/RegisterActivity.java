package com.example.finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.sqlite.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private ProgressBar progressBar;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usernameEditText = findViewById(R.id.usernm);
        passwordEditText = findViewById(R.id.psswr);
        registerButton = findViewById(R.id.btn_regist);
        progressBar = findViewById(R.id.progressBar);


        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                showProgressBar();
                if (databaseHelper.isUserExists(username)) {
                    usernameEditText.setError("Username already exists");
                    Toast.makeText(RegisterActivity.this, "username already exists. Please use a different username.", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.insertUser(username, password);
                    hideProgressBar();
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }
}
