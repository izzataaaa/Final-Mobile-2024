package com.example.finalproject.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.finalproject.EditProfileActivity;
import com.example.finalproject.EditProfileActivity;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.sqlite.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {

    TextView tv_welcome, tv_name, tv_number;
    ImageView img_logo;
    Button btn_edit, btn_logout;
    private ProgressBar progressBar;
    private int recordId;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        tv_welcome = view.findViewById(R.id.tv_welcome);
        btn_edit = view.findViewById(R.id.btn_edit);
        img_logo = view.findViewById(R.id.img_logo);
        btn_logout = view.findViewById(R.id.btn_logout);
        tv_name = view.findViewById(R.id.tv_name);
        tv_number = view.findViewById(R.id.tv_number);
        progressBar = view.findViewById(R.id.progressBar);


        loadUserData();

        btn_logout.setOnClickListener(v -> showLogoutConfirmationDialog());

        btn_edit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("USER_ID", recordId);
            startActivity(intent);
        });

    }

    private void loadUserData() {
        showProgressBar();
        try (SQLiteDatabase db = databaseHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     databaseHelper.getTableUser(),
                     new String[]{
                             databaseHelper.getColumnIdUser(),
                             databaseHelper.getColumnUsername(),
                             databaseHelper.getColumnPassword(),
                             databaseHelper.getColumnIsLoggedIn()
                     },
                     databaseHelper.getColumnIsLoggedIn() + " = ?",
                     new String[]{"1"},
                     null, null, null)) {

            if (cursor.moveToFirst()) {
                recordId = cursor.getInt(cursor.getColumnIndexOrThrow(databaseHelper.getColumnIdUser()));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.getColumnUsername()));

                tv_welcome.setText("Halo, " + username + "!");
                tv_name.setText(username);
                // Assuming you want to display the user ID or other placeholder in tv_number
                tv_number.setText(String.valueOf(recordId));
            }
        }finally {
            hideProgressBar(); 
        }
    }



    private void logoutUser() {
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(databaseHelper.getColumnIsLoggedIn(), 0);
            db.update(databaseHelper.getTableUser(), values, databaseHelper.getColumnIsLoggedIn() + " = ?", new String[]{"1"});
        }

        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                .setNegativeButton("No", null)
                .show();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Reload user data after editing
            loadUserData();
        }
        hideProgressBar();
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
