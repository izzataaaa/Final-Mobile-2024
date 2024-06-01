package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.finalproject.adapter.AirplaneAdapter;
import com.example.finalproject.fragment.AirplaneFragment;
import com.example.finalproject.fragment.ProfileFragment;
import com.example.finalproject.fragment.SaveFragment;
import com.example.finalproject.model.AirplaneModels;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements AirplaneAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fragmentManager = getSupportFragmentManager();
        AirplaneFragment airplaneFragment = new AirplaneFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(AirplaneFragment.class.getSimpleName());
        if (!(fragment instanceof AirplaneFragment)){
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_home, airplaneFragment, AirplaneFragment.class.getSimpleName())
                    .commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.navmenu);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.homebtn) {
                selectedFragment = new AirplaneFragment();
            } else if (item.getItemId() == R.id.svbtn) {
                // Add fragment for Save
                 selectedFragment = new SaveFragment();
            } else if (item.getItemId() == R.id.profilebtn) {
                // Add fragment for Profile
                 selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_home, selectedFragment)
                        .commit();

                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AirplaneModels item) {
        SaveFragment fragment = new SaveFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_home, fragment)
                .addToBackStack(null)
                .commit();
    }
}
