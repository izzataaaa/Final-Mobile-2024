package com.example.finalproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.fragment.AirplaneFragment;

public class AirplaneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_airplane);

        // Load AirplaneFragment
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AirplaneFragment airplaneFragment = new AirplaneFragment();
            fragmentTransaction.add(R.id.airline, airplaneFragment);
            fragmentTransaction.commit();
        }
    }
}
