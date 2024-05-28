package com.example.finalproject.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.sqlite.DatabaseHelper;

public class ProfileFragment extends Fragment {

    Button btn_logout;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(v -> {
            logoutUser();
        });
    }

    private void logoutUser() {
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(databaseHelper.COLUMN_IS_LOGGED_IN, 0);
            db.update(databaseHelper.TABLE_USER, values, databaseHelper.COLUMN_IS_LOGGED_IN + " = ?", new String[]{"1"});
        }

        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}