package com.example.finalproject.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.EditProfileActivity;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.sqlite.DatabaseHelper;

public class ProfileFragment extends Fragment {

    TextView tv_welcome, tv_name, tv_number;
    ImageView img_logo, iv_delete;
    Button btn_change, btn_logout;
    DatabaseHelper databaseHelper;
    private int recordId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        tv_welcome = view.findViewById(R.id.tv_welcome);
        tv_name = view.findViewById(R.id.tv_name);
        tv_number = view.findViewById(R.id.tv_number);
        btn_change = view.findViewById(R.id.btn_edit);
        iv_delete = view.findViewById(R.id.iv_delete);
        img_logo = view.findViewById(R.id.img_logo);
        btn_logout = view.findViewById(R.id.btn_logout);

        loadUserData();

        btn_logout.setOnClickListener(v -> showLogoutConfirmationDialog());

        btn_change.setOnClickListener(v -> {
            startActivityForResult(new Intent(getActivity(), EditProfileActivity.class), 1);
        });

        iv_delete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void loadUserData() {
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

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Account")
                .setMessage("Are you sure to delete the account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    databaseHelper.deleteUser(recordId);
                    logoutUser();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Reload user data after editing
            loadUserData();
        }
    }
}
